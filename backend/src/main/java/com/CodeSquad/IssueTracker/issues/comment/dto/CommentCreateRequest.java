package com.CodeSquad.IssueTracker.issues.comment.dto;

import jakarta.validation.constraints.NotBlank;

public record CommentCreateRequest (
        @NotBlank(message = "코멘트 작성 시 author는 비어있을 수 없습니다.")
        String author,
        @NotBlank(message = "코멘트 작성 시 content는 비어있을 수 없습니다.")
        String content
) {}
