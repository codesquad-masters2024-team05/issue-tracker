package com.CodeSquad.IssueTracker.issues;

import com.CodeSquad.IssueTracker.assignee.dao.AssigneeId;
import com.CodeSquad.IssueTracker.issues.issueLabel.dto.LabelId;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("issues")
public class Issue {
    @Id
    private Long issueId;

    private String title;

    private String author;

    private LocalDateTime publishedAt;

    private Boolean isClosed;

    private Long milestoneId;
    @Setter
    @MappedCollection(idColumn = "issue_id")
    private Set<AssigneeId> assignees;
    @Setter
    @MappedCollection(idColumn = "issue_id")
    private Set<LabelId> labels;

    public Issue(Long issueId, String title, String author, LocalDateTime publishedAt, Boolean isClosed, Long milestoneId) {
        this.issueId = issueId;
        this.title = title;
        this.author = author;
        this.publishedAt = publishedAt;
        this.isClosed = isClosed;
        this.milestoneId = milestoneId;
    }
}