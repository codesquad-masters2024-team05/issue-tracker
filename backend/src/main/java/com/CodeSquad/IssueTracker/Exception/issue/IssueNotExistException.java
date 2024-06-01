package com.CodeSquad.IssueTracker.Exception.issue;

public class IssueNotExistException extends RuntimeException {
    public IssueNotExistException(String message) {
        super(message);
    }
}
