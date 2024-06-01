package com.CodeSquad.IssueTracker.Exception.label;

public class EmptyLabelNameException extends RuntimeException {
    public EmptyLabelNameException(String message) {
        super(message);
    }
}