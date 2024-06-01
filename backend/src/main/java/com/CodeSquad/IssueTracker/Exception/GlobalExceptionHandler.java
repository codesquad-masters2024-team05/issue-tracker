package com.CodeSquad.IssueTracker.Exception;

import com.CodeSquad.IssueTracker.Exception.milestone.InvalidMilestoneRequestException;
import com.CodeSquad.IssueTracker.Exception.milestone.MilestoneNotFoundException;
import com.CodeSquad.IssueTracker.Exception.user.*;
import com.CodeSquad.IssueTracker.Exception.label.*;
import com.CodeSquad.IssueTracker.Exception.issue.AuthorNotFoundException;
import com.CodeSquad.IssueTracker.Exception.issue.InvalidIssueDataException;
import com.CodeSquad.IssueTracker.Exception.user.InvalidCredentialException;
import com.CodeSquad.IssueTracker.Exception.user.InvalidUserFormatException;
import com.CodeSquad.IssueTracker.Exception.user.UserNotLoginException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserIdAlreadyExistException.class)
    public ResponseEntity<String> handleUserIdExistException(UserIdAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidUserFormatException.class)
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidUserFormatException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotLoginException.class)
    public ResponseEntity<String> handleUserNotLoginException(UserNotLoginException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<String> handleInvalidCredentialException(InvalidCredentialException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    //milestone
    @ExceptionHandler(MilestoneNotFoundException.class)
    public ResponseEntity<String> handleMilestoneNotFoundException(MilestoneNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidMilestoneRequestException.class)
    public ResponseEntity<String> handleInvalidMilestoneRequestException(InvalidMilestoneRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(InvalidLabelIdException.class)
    public ResponseEntity<String> handleInvalidLabelIdException(InvalidLabelIdException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(EmptyLabelNameException.class)
    public ResponseEntity<String> handleEmptyLabelNameException(EmptyLabelNameException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidLabelColorException.class)
    public ResponseEntity<String> handleInvalidLabelColorException(InvalidLabelColorException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(LabelNotFoundException.class)
    public ResponseEntity<String> handleLabelNotFoundException(LabelNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(DuplicateLabelNameException.class)
    public ResponseEntity<String> handleDupliclateLabelNameException(DuplicateLabelNameException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body((ex.getMessage()));
    }
    @ExceptionHandler(InvalidLabelNameException.class)
    public  ResponseEntity<String> handleInvalidLabelNameException(InvalidLabelNameException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        // Get the first error message
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String errorMessage = (fieldError != null) ? fieldError.getDefaultMessage() : "Invalid request";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}