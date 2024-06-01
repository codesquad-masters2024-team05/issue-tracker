package com.CodeSquad.IssueTracker.Exception.issue;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(String message) {
        super(message);
    }
}
