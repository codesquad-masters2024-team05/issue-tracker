package com.CodeSquad.IssueTracker.issues.comment.dto;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CommentResponse {
    private Long commentId;
    private String content;
    private String author;
    private String publishedAt;
}
