package com.CodeSquad.IssueTracker.interceptor;

import com.CodeSquad.IssueTracker.Exception.user.UserNotLoginException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class CheckLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return true; // OPTIONS 요청은 바로 통과시킴
        }

        HttpSession session = request.getSession();
        if (session.getAttribute("userId") == null) {
            throw new UserNotLoginException("로그인이 필요합니다.");
        }
        return true;
    }
}
