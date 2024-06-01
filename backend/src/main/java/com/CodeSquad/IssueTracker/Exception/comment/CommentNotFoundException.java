package com.CodeSquad.IssueTracker.Exception.comment;

public class CommentNotFoundException extends RuntimeException{

    public CommentNotFoundException(String message) {
        super(message);
    }
}
