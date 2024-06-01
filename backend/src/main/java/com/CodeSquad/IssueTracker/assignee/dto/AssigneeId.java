package com.CodeSquad.IssueTracker.assignee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@AllArgsConstructor
@Table("assignees")
public class AssigneeId {
    String userId;
}
