package com.CodeSquad.IssueTracker.config;

import com.CodeSquad.IssueTracker.interceptor.CheckLoginInterceptor;
import com.CodeSquad.IssueTracker.user.jwtlogin.JwtUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final JwtUtil jwtUtil;

    public MvcConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new CheckLoginInterceptor(jwtUtil))
            .order(1)
            .addPathPatterns("/**")
            .excludePathPatterns("/login", "/registration/**", "/issue/check", "/auth/github/**", "/favicon.ico", "/error");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://d1h2zmtghg8ybi.cloudfront.net",
                        "https://d1h2zmtghg8ybi.cloudfront.net",
                        "http://localhost:3000",
                        "https://localhost:3000"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
