package com.CodeSquad.IssueTracker.Exception.milestone;

public class MilestoneNotFoundException extends RuntimeException{
    public MilestoneNotFoundException(String message) {
        super(message);
    }
}
