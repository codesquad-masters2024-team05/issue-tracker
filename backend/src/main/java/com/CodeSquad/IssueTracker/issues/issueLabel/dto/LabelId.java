package com.CodeSquad.IssueTracker.issues.issueLabel.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@AllArgsConstructor
@Table("issueLabel")
public class LabelId {
    Long labelId;
}
