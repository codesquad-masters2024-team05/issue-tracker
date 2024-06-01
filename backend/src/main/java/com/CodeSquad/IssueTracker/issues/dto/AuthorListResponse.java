package com.CodeSquad.IssueTracker.issues.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthorListResponse {
    private String userId;
}
