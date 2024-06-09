package com.CodeSquad.IssueTracker.labels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;

@Validated
public record LabelRequest(
        @NotBlank(message = "라벨 이름이 필요합니다.")
        String labelName,
        String description,
        @NotBlank(message = "텍스트 색상이 필요합니다.")
        @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "유효하지 않은 색상코드입니다.")
        String textColor,
        @NotBlank(message = "배경 색상이 필요합니다.")
        @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "유효하지 않은 색상코드입니다.")
        String bgColor
) {
}
