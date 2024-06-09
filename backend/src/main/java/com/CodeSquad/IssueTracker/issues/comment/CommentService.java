package com.CodeSquad.IssueTracker.issues.comment;

import com.CodeSquad.IssueTracker.Exception.comment.AuthorNotMatchedException;
import com.CodeSquad.IssueTracker.Exception.comment.CommentNotFoundException;
import com.CodeSquad.IssueTracker.issues.IssueService;
import com.CodeSquad.IssueTracker.issues.comment.dto.CommentCreateRequest;
import com.CodeSquad.IssueTracker.user.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final IssueService issueService;

    public CommentService(CommentRepository commentRepository, UserService userService, IssueService issueService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.issueService = issueService;
    }

    public void addComment(CommentCreateRequest request, Long issueId) {
        issueService.validateExistIssue(issueId);
        userService.validateExistUser(request.author());
        Comment comment = Comment.builder()
                .author(request.author())
                .content(request.content())
                .issueId(issueId)
                .publishedAt(LocalDateTime.now())
                .build();
        commentRepository.save(comment);
    }

    public void updateComment(CommentCreateRequest request, Long commentId) {
        Comment comment = validateExistComment(commentId);

        if (!comment.getAuthor().equals(request.author())) {
            throw new AuthorNotMatchedException("코멘트의 작성자만 수정이 가능합니다");
        }

        commentRepository.updateComment(commentId, request.content());
    }

    public Comment validateExistComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() ->
                        new CommentNotFoundException("해당 이슈가 존재하지 않습니다."));
    }
}
