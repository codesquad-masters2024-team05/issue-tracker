package com.CodeSquad.IssueTracker.issues.dto;


import com.CodeSquad.IssueTracker.issues.comment.dto.CommentResponse;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.relational.core.mapping.Column;

import java.util.List;

@Getter
@Builder
public class IssueListResponse {
    private Long issueId;
    private String title;
    private String publishedAt;
    private String author;
    @Column("isClosed")
    private boolean isClosed;

}
