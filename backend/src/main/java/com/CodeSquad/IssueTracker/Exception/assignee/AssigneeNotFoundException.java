package com.CodeSquad.IssueTracker.Exception.assignee;

public class AssigneeNotFoundException extends RuntimeException{
    public AssigneeNotFoundException(String message) {
        super(message);
    }
}
