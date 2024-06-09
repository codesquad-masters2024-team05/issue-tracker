package com.CodeSquad.IssueTracker.issues.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class IssueDetailAccess {
    private Long issueId;

    private String title;

    private String author;

    private LocalDateTime publishedAt;

    private Boolean isClosed;

    private String assignee;

    private Long labelId;

    private String labelName;

    private String bgColor;

    private String textColor;

    private Long milestoneId;
}
