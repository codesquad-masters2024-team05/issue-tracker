package com.CodeSquad.IssueTracker.issues.comment;

import com.CodeSquad.IssueTracker.issues.comment.dto.CommentCreateRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/issue/{issueId}/comment")
    public ResponseEntity<Void> addComment(@Valid @RequestBody CommentCreateRequest request,
                                           @PathVariable("issueId") Long issueId) {
        commentService.addComment(request, issueId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/issue/{issueId}/comment/{commentId}")
    public ResponseEntity<Void> updateComment(@Valid @RequestBody CommentCreateRequest request,
                                              @PathVariable("issueId") Long issueId,
                                              @PathVariable("commentId") Long commentId) {
        commentService.updateComment(request, commentId);
        return ResponseEntity.ok().build();
    }
}
