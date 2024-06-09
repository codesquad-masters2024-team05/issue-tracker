package com.CodeSquad.IssueTracker.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CurrentUserResponse {
    private String currentUser;
}

