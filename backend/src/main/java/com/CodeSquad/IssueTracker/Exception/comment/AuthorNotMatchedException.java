package com.CodeSquad.IssueTracker.Exception.comment;

public class AuthorNotMatchedException extends RuntimeException{
    public AuthorNotMatchedException(String message) {
        super(message);
    }
}
