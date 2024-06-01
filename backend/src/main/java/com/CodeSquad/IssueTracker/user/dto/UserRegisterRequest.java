package com.CodeSquad.IssueTracker.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegisterRequest (
        @NotBlank(message = "ID가 필요합니다.")
        @Size(min = 6, max = 16, message = "ID는 6~16자여야 합니다.")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "11ID는 알파벳과 숫자로만 이뤄져야 합니다.")
        String userId,
        @NotBlank(message = "비밀번호가 필요합니다.")
        @Size(min = 6, max = 12, message = "비밀번호는 6~12자여야 합니다.")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "11비밀번호는 알파벳과 숫자로만 이뤄져야 합니다.")
        String userPassword
){
}
