package com.CodeSquad.IssueTracker.Exception.label;

public class DuplicateLabelNameException extends RuntimeException {
    public DuplicateLabelNameException(String message) {
        super(message);
    }
}