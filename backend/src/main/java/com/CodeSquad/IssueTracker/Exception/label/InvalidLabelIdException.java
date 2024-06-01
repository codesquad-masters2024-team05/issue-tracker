package com.CodeSquad.IssueTracker.Exception.label;

public class InvalidLabelIdException extends RuntimeException {
    public InvalidLabelIdException(String message) {
        super(message);
    }
}