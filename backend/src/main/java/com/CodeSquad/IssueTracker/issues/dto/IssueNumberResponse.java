package com.CodeSquad.IssueTracker.issues.dto;

import lombok.Builder;

@Builder
public record IssueNumberResponse (
        Long openIssueCount,

        Long closeIssueCount)
{ }
