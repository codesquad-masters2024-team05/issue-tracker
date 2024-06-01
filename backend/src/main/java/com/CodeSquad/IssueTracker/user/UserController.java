package com.CodeSquad.IssueTracker.user;

import com.CodeSquad.IssueTracker.user.dto.LoginRequest;
import com.CodeSquad.IssueTracker.user.dto.UserRegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registerNewUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        userService.save(userRegisterRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest,
                                       HttpServletRequest request) {
        userService.isLoginRequestNotExists(loginRequest);
        userService.authenticate(loginRequest);
        userService.addLoginSession(loginRequest, request.getSession());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/registration/validate/{id}")
    public ResponseEntity<?> getValidationId(@PathVariable("id") String id) {
        if (userService.isUserIdDuplicated(id)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
