package com.CodeSquad.IssueTracker.interceptor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class CheckLoginInterceptorTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private MockHttpSession session;

    @BeforeEach
    public void setup() {
        session = new MockHttpSession();
        session.setAttribute("userId", "testUser"); // 세션에 사용자 ID 설정

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @DisplayName("로그인하지 않은 상태로 존재하지 않는 페이지에 접근 시, 404 코드를 반환해야한다.")
    @Test
    public void testNotExistPageWithoutLogin() throws Exception {
        mockMvc.perform(get("/notExistPage"))
                .andExpect(status().isNotFound());
    }

    @DisplayName("로그인 상태로 존재하지 않는 페이지에 접근 시, 404 코드를 반환해야한다.")
    @Test
    public void testNotExistPageWithLogin() throws Exception {
        mockMvc.perform(get("/notExistPage").session(session))
                .andExpect(status().isNotFound());
    }

    @DisplayName("로그인하지 않은 상태로 권한이 필요한 페이지에 접근 시, 401 코드를 반환해야한다.")
    @Test
    public void testNeedAuthPageWithoutLogin() throws Exception {
        mockMvc.perform(get("/issues"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("로그인 상태로 권한이 필요한 페이지에 접근 시, 200 코드를 반환해야한다.")
    @Test
    public void testNeedAuthPageWithLogin() throws Exception {
        mockMvc.perform(get("/issues").session(session))
                .andExpect(status().isOk());
    }
}