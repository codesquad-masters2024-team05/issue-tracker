package com.CodeSquad.IssueTracker.Exception.user;

public class UserNotLoginException extends RuntimeException{
    public UserNotLoginException(String message) {
        super(message);
    }
}
