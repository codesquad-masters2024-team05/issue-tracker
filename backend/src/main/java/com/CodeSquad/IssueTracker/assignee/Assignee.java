package com.CodeSquad.IssueTracker.assignee;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Builder
@Table("assignees")
public class Assignee {

    @Id
    private Long assigneeId;
    private Long issueId;
    private String userId;
}
