package com.CodeSquad.IssueTracker.interceptor;

import com.CodeSquad.IssueTracker.Exception.user.UserNotLoginException;
import com.CodeSquad.IssueTracker.user.jwtlogin.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class CheckLoginInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    public CheckLoginInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return true; // OPTIONS 요청은 바로 통과시킴
        }

        String token = jwtUtil.getTokenFromHeader(request);
        if (token == null) {

            throw new UserNotLoginException("토큰이 없습니다.");
        }

        try {
            String userId = jwtUtil.validateToken(token);
            // 토큰 검증 성공 시 추가 작업 수행 (예: 사용자 정보 설정 등)
            request.setAttribute("userId", userId);
        } catch (Exception e) {
            throw new UserNotLoginException("토큰이 유효하지 않습니다.");
        }

        return true;
    }
}
