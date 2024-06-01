package com.CodeSquad.IssueTracker.milestone.dto;

public record MilestoneRequest(Long milestoneId, String title,
                               String description, String deadline,
                               Integer totalIssue, Integer closedIssue)
{ }
