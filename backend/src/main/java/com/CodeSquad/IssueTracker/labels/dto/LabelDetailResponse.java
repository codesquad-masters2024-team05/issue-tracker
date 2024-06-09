package com.CodeSquad.IssueTracker.labels.dto;

import lombok.Builder;

@Builder
public record LabelDetailResponse(
        Long labelId, String labelName,
        String description, String textColor,
        String bgColor)
{ }
