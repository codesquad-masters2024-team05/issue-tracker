package com.CodeSquad.IssueTracker.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserLoginTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("사용자 로그인 성공")
    public void authenticate_ValidUserIdAndPassword_ReturnsTrue() {
        String userId = "validUser";
        String userPassword = "validPassword";
        User user = new User(userId, userPassword);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        boolean result = userService.authenticate(userId, userPassword);

        assertTrue(result);
    }

    @Test
    @DisplayName("사용자 로그인 실패 (다른 아이디 값인 경우)")
    public void authenticate_InvalidUserId_ReturnsFalse() {
        String userId = "invalidUser";
        String userPassword = "validPassword";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        boolean result = userService.authenticate(userId, userPassword);

        assertFalse(result);
    }

    @Test
    @DisplayName("사용자 로그인 실패 (다른 비밀번호 값인 경우)")
    public void authenticate_InvalidPassword_ReturnsFalse() {
        String userId = "validUser";
        String userPassword = "invalidPassword";
        User user = new User(userId, "validPassword");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        boolean result = userService.authenticate(userId, userPassword);

        assertFalse(result);
    }

    @Test
    @DisplayName("사용자 로그인 실패 (아이디가 null 값인 경우")
    public void authenticate_NullUserId_ReturnsFalse() {
        String userId = null;
        String userPassword = "validPassword";

        boolean result = userService.authenticate(userId, userPassword);

        assertFalse(result);
    }

    @Test
    @DisplayName("사용자 로그인 실패 (비밀번호가 null 값인 경우")
    public void authenticate_NullPassword_ReturnsFalse() {
        String userId = "validUser";
        String userPassword = null;
        User user = new User(userId, "validPassword");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        boolean result = userService.authenticate(userId, userPassword);

        assertFalse(result);
    }
}
