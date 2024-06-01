package com.CodeSquad.IssueTracker.labels.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LabelListResponse {
    private Long labelId;
    private String labelName;
    private String labelBgColor;
}
