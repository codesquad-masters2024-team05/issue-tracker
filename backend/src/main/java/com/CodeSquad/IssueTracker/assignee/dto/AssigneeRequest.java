package com.CodeSquad.IssueTracker.assignee.dto;

import java.util.Set;

public record AssigneeRequest (
        Set<String> assignees
){

}
