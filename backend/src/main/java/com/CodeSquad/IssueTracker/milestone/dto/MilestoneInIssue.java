package com.CodeSquad.IssueTracker.milestone.dto;

public record MilestoneInIssue(
        Long milestoneId,
        String title,
        Long totalIssue,
        Long closedIssue)
{ }
