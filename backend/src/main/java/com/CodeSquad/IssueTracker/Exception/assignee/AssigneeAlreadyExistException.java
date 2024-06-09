package com.CodeSquad.IssueTracker.Exception.assignee;

public class AssigneeAlreadyExistException extends RuntimeException{
    public AssigneeAlreadyExistException(String message) {
        super(message);
    }
}
