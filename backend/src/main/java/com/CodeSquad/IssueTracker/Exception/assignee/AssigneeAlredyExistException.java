package com.CodeSquad.IssueTracker.Exception.assignee;

public class AssigneeAlredyExistException extends RuntimeException{
    public AssigneeAlredyExistException(String message) {
        super(message);
    }
}
