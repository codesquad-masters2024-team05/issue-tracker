package com.CodeSquad.IssueTracker.issues.comment.dto;

import lombok.Getter;

@Getter
public class CommentResponse {
    private Long commentId;
    private String content;
    private String author;
    private String publishedAt;
}
