package com.finder.mypet.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finder.mypet.exception.AppException;
import com.finder.mypet.exception.ErrorCode;
import com.finder.mypet.user.dto.request.UserJoinRequest;
import com.finder.mypet.user.dto.request.UserLoginRequest;
import com.finder.mypet.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @Test
    @DisplayName("회원가입 성공")
    @WithMockUser
    void join() throws Exception {

        String userId ="jiyeon@google.com";
        String password = "1q2w3e4r";
        String nickname = "zee";

        mockMvc.perform(post("/user/join")
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userId, password, nickname))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 실패 - 아이디(이메일) 중복")
    @WithMockUser
    void join_fail() throws Exception {

        String userId ="jiyeon@google.com";
        String password = "1q2w3e4r";
        String nickname = "zee";

        when(userService.join(any(), any(), any()))
                .thenThrow(new RuntimeException("해당 userId가 중복입니다."));


        mockMvc.perform(post("/user/join")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userId, password, nickname))))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("로그인 성공")
    @WithMockUser
    void login_success() throws Exception {

        String userId ="jiyeon@google.com";
        String password = "1q2w3e4r";

        when(userService.login(any(), any()))
                .thenReturn("token");

        mockMvc.perform(post("/user/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userId, password))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 실패 - userId 존재하지 않음")
    @WithMockUser
//    @WithAnonymousUser 뒤로 안넘어가는 이슈 발생
    void login_fail1() throws Exception {

        String userId ="jiyeon@google.com";
        String password = "1q2w3e4r";

        when(userService.login(any(), any()))
                .thenThrow(new AppException(ErrorCode.USERID_NOTFOUND, ""));

        mockMvc.perform(post("/user/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userId, password))))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("로그인 실패 - password 일치하지 않음")
    @WithMockUser
    void login_fail2() throws Exception {

        String userId ="jiyeon@google.com";
        String password = "1q2w3e4r";

        when(userService.login(any(), any()))
                .thenThrow(new AppException(ErrorCode.INVALID_PASSWORD, ""));

        mockMvc.perform(post("/user/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userId, password))))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
