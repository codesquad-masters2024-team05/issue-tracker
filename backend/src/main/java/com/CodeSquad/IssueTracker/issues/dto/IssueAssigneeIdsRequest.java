package com.CodeSquad.IssueTracker.issues.dto;

import java.util.Set;

public record IssueAssigneeIdsRequest(
        Set<String> assignees
){

}
