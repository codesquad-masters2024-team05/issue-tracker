package com.CodeSquad.IssueTracker.issues.issueLabel;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Data
@Table("issueLabel")
public class IssueLabel {
    @Id
    private Long issueId;
    private Long labelId;
    public IssueLabel(Long issueId, Long labelId) {
        this.issueId = issueId;
        this.labelId = labelId;
    }
}