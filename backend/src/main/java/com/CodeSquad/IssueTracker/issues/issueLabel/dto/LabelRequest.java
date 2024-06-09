package com.CodeSquad.IssueTracker.issues.issueLabel.dto;

import lombok.*;


@Builder
public record LabelRequest (
        Long labelId,
        String labelName,
        String bgColor,
        String textColor
){
}
