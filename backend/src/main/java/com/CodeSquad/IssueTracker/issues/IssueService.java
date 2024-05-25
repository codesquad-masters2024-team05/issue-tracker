package com.CodeSquad.IssueTracker.issues;

import com.CodeSquad.IssueTracker.Exception.issue.InvalidIssuePageException;
import com.CodeSquad.IssueTracker.Exception.issue.IssueNotExistException;
import com.CodeSquad.IssueTracker.assignee.AssigneeRepository;
import com.CodeSquad.IssueTracker.assignee.AssigneeService;
import com.CodeSquad.IssueTracker.assignee.dto.AssigneeId;
import com.CodeSquad.IssueTracker.issues.comment.Comment;
import com.CodeSquad.IssueTracker.issues.comment.CommentRepository;
import com.CodeSquad.IssueTracker.issues.comment.dto.CommentResponse;
import com.CodeSquad.IssueTracker.issues.dto.*;
import com.CodeSquad.IssueTracker.issues.issueLabel.IssueLabelService;
import com.CodeSquad.IssueTracker.issues.issueLabel.dto.LabelId;
import com.CodeSquad.IssueTracker.issues.issueLabel.dto.LabelRequest;
import com.CodeSquad.IssueTracker.labels.LabelService;
import com.CodeSquad.IssueTracker.milestone.Milestone;
import com.CodeSquad.IssueTracker.milestone.MilestoneService;
import com.CodeSquad.IssueTracker.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class IssueService {
    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;
    private final MilestoneService milestoneService;
    private final LabelService labelService;
    private final AssigneeService assigneeService;
    private final UserService userService;
    private final IssueLabelService issueLabelService;

    public IssueService(IssueRepository issueRepository, CommentRepository commentRepository,
                        UserService userService, MilestoneService milestoneService,
                        LabelService labelService, AssigneeService assigneeService,
                        IssueLabelService issueLabelService) {
        this.issueRepository = issueRepository;
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.milestoneService = milestoneService;
        this.labelService = labelService;
        this.assigneeService = assigneeService;
        this.issueLabelService = issueLabelService;
    }


    public List<Issue> getAllIssues() {
        List<Issue> issues = (List<Issue>) issueRepository.findAll();
        log.info("Retrieved issues: {}", issues);
        return (List<Issue>) issueRepository.findAll();
    }

    public Long createIssue(IssueRequest issueRequest) {
        validateIssueRequest(issueRequest);
        assigneeService.validateAssigneeIds(issueRequest.assignees());
        labelService.validateLabels(issueRequest.labels());
        if (issueRequest.milestone() != null)
            milestoneService.validateMilestoneId(issueRequest.milestone());

        log.info("Creating issue: {}", issueRequest);
        Set<AssigneeId> assigneeIds = issueRequest.assignees().stream()
                                                    .map(AssigneeId::new)
                                                    .collect(Collectors.toSet());

        Set<LabelId> labelIds = issueRequest.labels().stream()
                                            .map(LabelId::new)
                                            .collect(Collectors.toSet());



        // 이슈 저장을 위한 객체 생성
        Issue issue = Issue.builder()
                .title(issueRequest.title())
                .author(issueRequest.author())
                .publishedAt(LocalDateTime.now())
                .isClosed(false)
                .milestoneId(issueRequest.milestone())
                .assignees(assigneeIds)
                .labels(labelIds)
                .build();

        issueRepository.save(issue);
        if (issue.getMilestoneId() != null){
            milestoneService.incrementTotalIssue(issue.getMilestoneId());
        }

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
        Issue issue = findIssueById(issueId);

        List<CommentResponse> comments = commentRepository.findByIssueId(issueId);

        List<String> assignees = assigneeService.findUsersByIssueId(issueId);
        List<LabelRequest> labels = issueLabelService.getLabelsByIssueId(issueId);

        return IssueDetailResponse.builder()
                .issueId(issue.getIssueId())
                .title(issue.getTitle())
                .author(issue.getAuthor())
                .publishedAt(issue.getPublishedAt().toString())
                .isClosed(issue.getIsClosed())
                .comments(comments)
                .assignees(assignees)
                .labels(labels)
                .milestone(issue.getMilestoneId())
                .build();
    }
    public void validateIssueListPage(long page) {
        if (page < 1) {
            throw new InvalidIssuePageException("page는 1 이상의 정수여야 합니다.");
        }
    }

    @Transactional
    public void openIssue(long issueId) {
        Issue issue = findIssueById(issueId);
        issueRepository.openIssue(issueId);

        if (issue.getMilestoneId() != null){
            milestoneService.decrementClosedIssue(issue.getMilestoneId());
        }
    }

    @Transactional
    public void closeIssue(long issueId) {
        Issue issue = findIssueById(issueId);
        issueRepository.closeIssue(issueId);

        if (issue.getMilestoneId() != null){
            milestoneService.incrementClosedIssue(issue.getMilestoneId());
        }
    }

    public void updateIssueTitle(Long issueId, IssueTitleRequest issueTitleRequest) {
        findIssueById(issueId);
        issueRepository.updateIssueTitle(issueId, issueTitleRequest.title());
    }

    @Transactional
    public void updateAssignees(Long issueId, Set<String> newAssignees) {
        Issue issue = findIssueById(issueId);
        assigneeService.validateAssigneeIds(newAssignees);

        Set<AssigneeId> assigneeIds = newAssignees.stream()
                .map(AssigneeId::new)
                .collect(Collectors.toSet());

        issue.setAssignees(assigneeIds);

        issueRepository.save(issue);
    }

    @Transactional
    public void updateLabels(Long issueId, Set<Long> newLabels) {
        labelService.validateLabels(newLabels);

        Issue issue = findIssueById(issueId);

        Set<LabelId> labelIds = newLabels.stream()
                .map(LabelId::new)
                .collect(Collectors.toSet());

        issue.setLabels(labelIds);

        issueRepository.save(issue);
    }



    private void validateIssueRequest(IssueRequest issueRequest) {
        userService.validateExistUser(issueRequest.author());
    }

    @Transactional
    public Milestone updateMilestoneIdForIssue(Long issueId, IssueMilestoneRequest issueMilestoneRequest) {
        Issue issue = findIssueById(issueId);
        Long milestoneId = issueMilestoneRequest.milestoneId();
        log.info("Updating milestone id for issue: {}", milestoneId);
        if (issue.getMilestoneId() == null) {
            // 이슈에 마일스톤 ID가 없는 경우
            milestoneService.incrementTotalIssue(milestoneId);
            if (issue.getIsClosed()) {
                milestoneService.incrementClosedIssue(milestoneId);
            }
            issueRepository.updateMilestoneIdForIssue(issueId, milestoneId);
            return milestoneService.getMilestoneById(milestoneId);
        } else
        if (milestoneId != null) {
            // 마일스톤 ID가 존재하는 경우 업데이트
            milestoneService.decrementTotalIssue(issue.getMilestoneId());
            milestoneService.incrementTotalIssue(milestoneId);

            if (issue.getIsClosed()) {
                milestoneService.decrementClosedIssue(issue.getMilestoneId());
                milestoneService.incrementClosedIssue(milestoneId);
            }

            issueRepository.updateMilestoneIdForIssue(issueId, milestoneId);
            return milestoneService.getMilestoneById(milestoneId);
        } else {
            // 마일스톤 ID가 null인 경우 해당 이슈의 마일스톤을 삭제
            milestoneService.decrementTotalIssue(issue.getMilestoneId());

            if (issue.getIsClosed()) {
                milestoneService.decrementClosedIssue(issue.getMilestoneId());
            }

            issueRepository.removeMilestoneFromIssue(issueId);
            return null;
        }
    }

    @Transactional
    public void openIssues(IssueIds issueIds) {
        for (Long issueId : issueIds.issueIds()) {
            openIssue(issueId);
        }
    }

    @Transactional
    public void closeIssues(IssueIds issueIds) {
        for (Long issueId : issueIds.issueIds()) {
            closeIssue(issueId);
        }
    }

    public IssueNumberResponse getIssueNumber() {
        long openIssueNum = issueRepository.countOpenIssues();
        long closeIssueNum = issueRepository.countCloseIssues();
        return IssueNumberResponse.builder()
                .openIssueCount(openIssueNum)
                .closeIssueCount(closeIssueNum)
                .build();
    }

    public void validateExistIssue(Long issueId) {
        issueRepository.findById(issueId)
                .orElseThrow(() -> new IssueNotExistException("이슈가 존재하지 않습니다."));
    }
}
