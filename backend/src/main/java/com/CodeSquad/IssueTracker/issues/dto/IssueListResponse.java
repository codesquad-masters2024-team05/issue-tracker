package com.CodeSquad.IssueTracker.issues.dto;


import com.CodeSquad.IssueTracker.issues.issueLabel.dto.LabelRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class IssueListResponse {
    private Long issueId;

    private String title;

    private String author;

    private LocalDateTime publishedAt;

    private Boolean isClosed;

    @Setter
    private List<String> assignees;

    @Setter
    private List<LabelRequest> labels;

    private Long milestoneId;

}
