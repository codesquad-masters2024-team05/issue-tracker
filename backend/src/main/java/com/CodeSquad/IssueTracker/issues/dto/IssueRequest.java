package com.CodeSquad.IssueTracker.issues.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Optional;
import java.util.Set;

public record IssueRequest(
        @NotBlank(message = "제목이 필요합니다.")
        @Size(max = 60, message = "제목이 너무 깁니다.")
        String title,
        Optional<String> content,
        @NotBlank(message = "작성자 정보가 필요합니다.")
        String author,
        Set<String> assignees,
        Set<Long> labels,
        Long milestone)
{
        public IssueRequest {
        // null인 경우 빈 컬렉션으로 초기화
        // milestoneId가 null인 경우는 서비스에 처리
        if (assignees == null) {
                assignees = Set.of();
        }
        if (labels == null) {
                labels = Set.of();
        }
        }
}