package com.CodeSquad.IssueTracker.issues.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record IssueTitleRequest(
        @NotBlank(message = "제목이 필요합니다.")
        @Size(max = 60, message = "제목이 너무 깁니다.")
        String title)
{ }
