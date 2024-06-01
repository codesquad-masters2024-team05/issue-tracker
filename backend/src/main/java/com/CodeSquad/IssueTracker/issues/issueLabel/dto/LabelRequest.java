package com.CodeSquad.IssueTracker.issues.issueLabel.dto;

import lombok.*;

@Data
@Builder
public class LabelRequest {
    private Long labelId;
    private String labelName;
    private String textColor;
    private String bgColor;
}
