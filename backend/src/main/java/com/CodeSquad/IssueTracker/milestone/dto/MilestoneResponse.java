package com.CodeSquad.IssueTracker.milestone.dto;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record MilestoneResponse(
        Long milestoneId, String title,
        String description, LocalDateTime deadline,
        Integer totalIssue, Integer closedIssue,
        Boolean isClosed)
{ }
