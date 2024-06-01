package com.CodeSquad.IssueTracker.Exception.user;

public class InvalidUserFormatException extends RuntimeException {
    public InvalidUserFormatException(String message) {
        super(message);
    }
}
