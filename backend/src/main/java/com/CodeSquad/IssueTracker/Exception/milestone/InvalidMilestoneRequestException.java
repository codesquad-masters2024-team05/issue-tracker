package com.CodeSquad.IssueTracker.Exception.milestone;

public class InvalidMilestoneRequestException extends RuntimeException {
    public InvalidMilestoneRequestException(String message) {
        super(message);
    }
}
