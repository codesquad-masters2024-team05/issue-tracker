package com.CodeSquad.IssueTracker.user;

import com.CodeSquad.IssueTracker.Exception.user.InvalidUserFormatException;
import com.CodeSquad.IssueTracker.user.dto.UserRegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserRegistrationTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    @DisplayName("유효한 사용자의 회원가입")
    public void testSaveValidUser() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest("validU", "vidPas");
        User user = new User("validU", "vidPas");
        assertTrue(userService.save(userRegisterRequest));
    }


    @Test
    @DisplayName("유효하지 않은 사용자의 가입")
    public void testSaveInvalidUser() {
        User user = new User("invalidUser", "invalidPassword");
        InvalidUserFormatException exception = assertThrows(InvalidUserFormatException.class, () -> {
            userService.save(user);
        });

        assertEquals("비밀번호가 형식에 맞지 않습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("중복된 ID가 있을 때")
    public void testIsUserIdDuplicated() {
        String userId = "existingUserId";
        User user = new User(userId, "invalidPassword");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        assertTrue(userService.isUserIdDuplicated(userId));
    }

    @Test
    @DisplayName("중복된 ID가 없을 때")
    public void testIsUserIdNotDuplicated() {
        String existId = "existingUserId";
        User user = new User(existId, "invalidPassword");

        String userId = "newUserId";
        when(userRepository.findById(existId)).thenReturn(Optional.of(user));
        assertFalse(userService.isUserIdDuplicated(userId));
    }
}