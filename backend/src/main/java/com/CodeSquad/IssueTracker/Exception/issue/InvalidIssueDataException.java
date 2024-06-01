package com.CodeSquad.IssueTracker.Exception.issue;

public class InvalidIssueDataException extends RuntimeException {
    public InvalidIssueDataException(String message) {
        super(message);
    }
}
