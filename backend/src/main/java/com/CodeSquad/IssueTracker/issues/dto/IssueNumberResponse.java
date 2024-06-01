package com.CodeSquad.IssueTracker.issues.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IssueNumberResponse {
    private Long openIssueCount;
    private Long closeIssueCount;
}
