package com.CodeSquad.IssueTracker.Exception;

import com.CodeSquad.IssueTracker.Exception.comment.AuthorNotMatchedException;
import com.CodeSquad.IssueTracker.Exception.comment.CommentNotFoundException;
import com.CodeSquad.IssueTracker.Exception.issue.AuthorNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommentExcpetionHandler {

    @ExceptionHandler(AuthorNotMatchedException.class)
    public ResponseEntity<String> handleAuthorNotMatchedException(AuthorNotMatchedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<String> handleCommentNotFoundException(CommentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
