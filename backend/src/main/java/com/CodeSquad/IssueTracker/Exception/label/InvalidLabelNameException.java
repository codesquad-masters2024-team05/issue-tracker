package com.CodeSquad.IssueTracker.Exception.label;

public class InvalidLabelNameException extends RuntimeException {
    public InvalidLabelNameException(String message) {
        super(message);
    }
}