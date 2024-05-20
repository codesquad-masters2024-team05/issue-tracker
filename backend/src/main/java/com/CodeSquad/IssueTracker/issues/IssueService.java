package com.CodeSquad.IssueTracker.issues;

import com.CodeSquad.IssueTracker.Exception.issue.AuthorNotFoundException;
import com.CodeSquad.IssueTracker.Exception.issue.InvalidIssueDataException;
import com.CodeSquad.IssueTracker.Exception.issue.InvalidIssuePageException;
import com.CodeSquad.IssueTracker.Exception.issue.IssueNotExistException;
import com.CodeSquad.IssueTracker.Exception.label.LabelNotFoundException;
import com.CodeSquad.IssueTracker.issues.comment.Comment;
import com.CodeSquad.IssueTracker.issues.comment.CommentRepository;
import com.CodeSquad.IssueTracker.issues.comment.dto.CommentResponse;
import com.CodeSquad.IssueTracker.issues.dto.IssueDetailResponse;
import com.CodeSquad.IssueTracker.issues.dto.IssueIds;
import com.CodeSquad.IssueTracker.issues.dto.IssueRequest;
import com.CodeSquad.IssueTracker.issues.issueLabel.*;
import com.CodeSquad.IssueTracker.issues.issueLabel.dto.LabelRequest;
import com.CodeSquad.IssueTracker.labels.Label;
import com.CodeSquad.IssueTracker.labels.LabelRepository;
import com.CodeSquad.IssueTracker.milestone.MilestoneRepository;
import com.CodeSquad.IssueTracker.user.User;
import com.CodeSquad.IssueTracker.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class IssueService {
    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final MilestoneRepository milestoneRepository;
    private final LabelRepository labelRepository;
    private final IssueLabelRepository issueLabelRepository;
    public IssueService(IssueRepository issueRepository, CommentRepository commentRepository,
                        UserRepository userRepository, MilestoneRepository milestoneRepository,
                        LabelRepository labelRepository, IssueLabelRepository issueLabelRepository) {
        this.issueRepository = issueRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.milestoneRepository = milestoneRepository;
        this.labelRepository = labelRepository;
        this.issueLabelRepository = issueLabelRepository;
    }


    public List<Issue> getAllIssues() {
        List<Issue> issues = (List<Issue>) issueRepository.findAll();
        log.info("Retrieved issues: {}", issues);
        return (List<Issue>) issueRepository.findAll();
    }

    public Long createIssue(IssueRequest issueRequest) {
        validateIssueRequest(issueRequest);

        log.info("Creating issue: {}", issueRequest);

        // 이슈 저장을 위한 객체 생성
        Issue issue = Issue.builder()
                .title(issueRequest.title())
                .author(issueRequest.author())
                .publishedAt(LocalDateTime.now())
                .isClosed(false)
                .milestoneId(issueRequest.milestoneId())
                .build();

        issueRepository.save(issue);
        milestoneRepository.incrementIssueCountForMilestone(issue.getMilestoneId());

        // 이슈 작성 시 입력한 내용을 첫번째 코멘트로 저장하기 위함.
        Comment comment = Comment.builder()
                .author(issueRequest.author())
                .content(issueRequest.content().
                        filter(content -> !content.isEmpty()).
                        orElse("이슈 작성자의 설명이 제공되지 않았습니다."))
                .publishedAt(LocalDateTime.now())
                // save 메소드가 호출된 후, @ID 식별자로 지정된 필드에 자동생성된 ID가 설정되어 이용할 수 있다.
                .issueId(issue.getIssueId())
                .build();

        commentRepository.save(comment);

        return issue.getIssueId();
    }

    public Issue findIssueById(long issueId) {
        return issueRepository.findById(issueId)
                .orElseThrow(() ->
                        new IssueNotExistException("존재하지 않는 이슈입니다."));
    }

    public List<Issue> findOpenIssues(long page, long limit) {
        validateIssueListPage(page);
        long offset = (page - 1) * limit;
        return issueRepository.findOpenIssues(limit, offset);
    }

    public List<Issue> findCloseIssues(long page, long limit) {
        validateIssueListPage(page);
        long offset = (page - 1) * limit;
        return issueRepository.findCloseIssues(limit, offset);
    }

    public IssueDetailResponse getIssueById(long issueId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IssueNotExistException("존재하지 않는 이슈입니다."));

        List<CommentResponse> comments = commentRepository.findByIssueId(issueId);
        List<LabelRequest> issueLabels = issueLabelRepository.findByIssueId(issueId);
        List<LabelRequest> labelResponses = issueLabels.stream()
                .map(issueLabel -> {
                    Optional<Label> label = labelRepository.findById(issueLabel.getLabelId());
                    return label.map(l -> LabelRequest.builder()
                                    .labelId(l.getLabelId())
                                    .labelName(l.getLabelName())
                                    .textColor(l.getTextColor())
                                    .bgColor(l.getBgColor())
                                    .build())
                            .orElse(null);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return IssueDetailResponse.builder()
                .issueId(issue.getIssueId())
                .title(issue.getTitle())
                .author(issue.getAuthor())
                .publishedAt(issue.getPublishedAt().toString())
                .isClosed(issue.getIsClosed())
                .comments(comments)
                .labels(labelResponses)
                .build();
    }
    public void validateIssueListPage(long page) {
        if (page < 1) {
            throw new InvalidIssuePageException("page는 1 이상의 정수여야 합니다.");
        }
    }

    public void openIssue(long issueId) {
        issueRepository.findById(issueId)
                .orElseThrow(() ->
                        new IssueNotExistException("존재하지 않는 이슈입니다."));
        issueRepository.openIssue(issueId);
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() ->
                        new IssueNotExistException("존재하지 않는 이슈입니다."));
        milestoneRepository.decrementClosedIssueCountForMilestone(issue.getMilestoneId());
    }

    public void closeIssue(long issueId) {
        issueRepository.findById(issueId)
                .orElseThrow(() ->
                        new IssueNotExistException("존재하지 않는 이슈입니다."));
        issueRepository.closeIssue(issueId);
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() ->
                        new IssueNotExistException("존재하지 않는 이슈입니다."));
        milestoneRepository.incrementClosedIssueCountForMilestone(issue.getMilestoneId());
    }

    private void validateIssueRequest(IssueRequest issueRequest) {
        Optional<User> user = userRepository.findById(issueRequest.author());
        if (user.isEmpty()) {
            throw new AuthorNotFoundException("작성자가 유효하지 않습니다. : " + issueRequest.author());
        }

        if (issueRequest.title() == null || issueRequest.title().isEmpty()) {
            throw new InvalidIssueDataException("제목이 필요합니다.");
        }
    }
    public void addLabelToIssue(Long issueId, Long labelId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IssueNotExistException("해당 이슈가 존재하지 않습니다. issueId=" + issueId));

        Label label = labelRepository.findById(labelId)
                .orElseThrow(() -> new LabelNotFoundException("해당 라벨이 존재하지 않습니다. labelId=" + labelId));

        issueLabelRepository.addLabelToIssue(issueId, labelId);
    }

    public void removeLabelFromIssue(Long issueId, Long labelId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IssueNotExistException("해당 이슈가 존재하지 않습니다. issueId=" + issueId));

        Label label = labelRepository.findById(labelId)
                .orElseThrow(() -> new LabelNotFoundException("해당 라벨이 존재하지 않습니다. labelId=" + labelId));

        issueLabelRepository.removeLabelFromIssue(issueId, labelId);
    }

    public void openIssues(IssueIds issueIds) {
        issueRepository.openIssues(issueIds.issueIds());
    }

    public void closeIssues(IssueIds issueIds) {
        issueRepository.closeIssues(issueIds.issueIds());
    }
}
