package com.CodeSquad.IssueTracker.Exception.user;

public class UserIdAlreadyExistException extends RuntimeException {
    public UserIdAlreadyExistException(String message) {
        super(message);
    }
}
