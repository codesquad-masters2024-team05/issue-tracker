package com.CodeSquad.IssueTracker.issues.issueLabel.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record IssueLabelRequest (

        @NotNull(message = "labelId는 null일 수 없습니다.")
        Set<Long> labelIds
){
}
