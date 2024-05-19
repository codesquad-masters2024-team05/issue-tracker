package com.CodeSquad.IssueTracker.filter;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IssueNumberResponse {
    private Long openIssueCount;
    private Long closeIssueCount;
}
