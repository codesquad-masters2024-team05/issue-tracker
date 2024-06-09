package com.CodeSquad.IssueTracker.user;

import com.CodeSquad.IssueTracker.user.dto.CurrentUserResponse;
import com.CodeSquad.IssueTracker.user.dto.LoginRequest;
import com.CodeSquad.IssueTracker.user.dto.UserRegisterRequest;
import com.CodeSquad.IssueTracker.user.jwtlogin.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        String token = userService.authenticate(loginRequest);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/registration/validate/{id}")
    public ResponseEntity<?> getValidationId(@PathVariable("id") String id) {
        if (userService.isUserIdDuplicated(id)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
  
    @GetMapping("/users")
    public ResponseEntity<List<String>> getUserListForIssued() {
        List<String> userIdList = userService.getAllUserIds();
        return ResponseEntity.ok(userIdList);
    }

    @GetMapping("/currentuser")
    public ResponseEntity<CurrentUserResponse> getCurrentUserId(HttpServletRequest request) {
        CurrentUserResponse currentUserResponse = userService.getCurrentUser(request);
        return ResponseEntity.ok(currentUserResponse);
    }
}
