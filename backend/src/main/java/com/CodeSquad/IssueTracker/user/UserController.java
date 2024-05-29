package com.CodeSquad.IssueTracker.user;

import com.CodeSquad.IssueTracker.user.dto.LoginRequest;
import com.CodeSquad.IssueTracker.user.dto.UserRegisterRequest;
import com.CodeSquad.IssueTracker.user.jwtlogin.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
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

//    세션 로그인 방식
//        @PostMapping("/login")
//    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest,
//                                       HttpServletRequest request) {
//        userService.isLoginRequestNotExists(loginRequest);
//        userService.authenticate(loginRequest);
//        userService.addLoginSession(loginRequest, request.getSession());
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
