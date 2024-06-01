package com.CodeSquad.IssueTracker.Exception.user;

public class InvalidCredentialException extends RuntimeException{
    public InvalidCredentialException(String message) {
        super(message);
    }
}