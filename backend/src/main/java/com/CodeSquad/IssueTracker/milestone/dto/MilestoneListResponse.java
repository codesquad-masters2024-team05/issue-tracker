package com.CodeSquad.IssueTracker.milestone.dto;

import lombok.Builder;

@Builder
public record MilestoneListResponse (
        Long milestoneId, String title)
{ }
