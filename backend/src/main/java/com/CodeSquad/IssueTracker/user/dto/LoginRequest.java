package com.CodeSquad.IssueTracker.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter

public class LoginRequest {
    @NotBlank(message = "ID가 필요합니다.")
    @Size(min = 6, max = 16, message = "ID는 6~16자여야 합니다.")
    private String userId;

    @NotBlank(message = "ID가 필요합니다.")
    @Size(min = 6, max = 12, message = "비밀번호는 6~12자여야 합니다.")
    private String userPassword;
}
