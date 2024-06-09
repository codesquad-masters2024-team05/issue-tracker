package com.CodeSquad.IssueTracker.Exception;

import com.CodeSquad.IssueTracker.Exception.issue.AuthorNotFoundException;
import com.CodeSquad.IssueTracker.Exception.issue.InvalidIssueDataException;
import com.CodeSquad.IssueTracker.Exception.issue.InvalidIssuePageException;
import com.CodeSquad.IssueTracker.Exception.issue.IssueNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class IssueExceptionHandler {
    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<String> handleAuthorNotFoundException(AuthorNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidIssueDataException.class)
    public ResponseEntity<String> handleInvalidIssueDataException(InvalidIssueDataException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidIssuePageException.class)
    public ResponseEntity<String> handleInvalidIssuePageException(InvalidIssuePageException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(IssueNotExistException.class)
    public ResponseEntity<String> handleIssueNotExistException(IssueNotExistException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
