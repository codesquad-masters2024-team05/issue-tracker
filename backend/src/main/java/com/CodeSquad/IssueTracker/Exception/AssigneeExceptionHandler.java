package com.CodeSquad.IssueTracker.Exception;

import com.CodeSquad.IssueTracker.Exception.assignee.AssigneeAlreadyExistException;
import com.CodeSquad.IssueTracker.Exception.assignee.AssigneeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AssigneeExceptionHandler {

    @ExceptionHandler(AssigneeAlreadyExistException.class)
    public ResponseEntity<String> handleAssigneeAlredyExistException(AssigneeAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(AssigneeNotFoundException.class)
    public ResponseEntity<String> handleAssigneeNotFoundException(AssigneeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
