package com.CodeSquad.IssueTracker.issues.dto;

import com.CodeSquad.IssueTracker.issues.comment.dto.CommentResponse;
import com.CodeSquad.IssueTracker.issues.issueLabel.dto.LabelRequest;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.relational.core.mapping.Column;

import java.util.List;

@Getter
@Builder
public class IssueDetailResponse {
    private Long issueId;
    private String title;
    private String author;
    private String publishedAt;
    @Column("isClosed")
    private boolean isClosed;
    private List<CommentResponse> comments;
    private List<LabelRequest> labels;
    private List<String> assignees;
    private Long milestone;
}
