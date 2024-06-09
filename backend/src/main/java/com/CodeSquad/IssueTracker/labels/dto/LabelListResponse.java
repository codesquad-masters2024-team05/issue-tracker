package com.CodeSquad.IssueTracker.labels.dto;

import lombok.Builder;

@Builder
public record LabelListResponse(
        Long labelId,
        String labelName,
        String labelBgColor,
        String labelTextColor)
{ }
