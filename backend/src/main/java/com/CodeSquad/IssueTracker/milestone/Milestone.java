package com.CodeSquad.IssueTracker.milestone;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Setter
@Getter

@Table("milestone")
public class Milestone {
    @Id
    private Long milestoneId;

    private String title;

    private String description;

    private LocalDateTime deadline;

    private Integer totalIssue = 0;

    private Integer closedIssue = 0;

    private Boolean isClosed = false;

    public Milestone(Long milestoneId, String title, String description, LocalDateTime deadline) {
        this.milestoneId = milestoneId;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }
}
