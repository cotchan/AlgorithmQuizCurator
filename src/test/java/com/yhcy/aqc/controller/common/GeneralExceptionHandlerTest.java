package com.yhcy.aqc.controller.common;


import com.yhcy.aqc.controller.authentication.AuthenticationRestController;
import com.yhcy.aqc.controller.quiz.QuizRestController;
import com.yhcy.aqc.controller.user.UserRestController;
import com.yhcy.aqc.error.NotFoundException;
import com.yhcy.aqc.error.UnauthorizedException;
import com.yhcy.aqc.security.WithMockCustomUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class GeneralExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserRestController userRestController;

    @MockBean
    QuizRestController quizRestController;

    @MockBean
    AuthenticationRestController authenticationRestController;

    @Test
    public void IllegalArgumentException_테스트() throws Exception {

        /**
         *     private final String id;
         *     private final String pw;
         *     private final String pwConfirm;
         *     private final String nickname;
         *     private final String verifyQuestion;
         *     private final String verifyAnswer;
         */
        //given
        Mockito.when(userRestController.joinProcess(any())).thenThrow(new IllegalArgumentException("입력 양식이 올바르지 않습니다."));
        String requestBody = "{\"id\":\"kevin\",\"pw\":\"1234\",\"pwConfirm\":\"1234\",\"nickname\":\"kevin\",\"verifyQuestion\":\"1\",\"verifyAnswer\":\"kevin\"}";

        //when
        mockMvc.perform(post("/api/user/join").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value("false"))
                .andExpect(jsonPath("$.error.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(result -> {
                    assertThat(getApiResultExceptionClass(result)).isEqualTo(IllegalArgumentException.class);
                });
    }

    // ========== HTTP 401 오류 처리 ==========
    @Test
    public void UnauthorizedException_테스트() throws Exception {

        //given
        Mockito.when(authenticationRestController.authentication(any())).thenThrow(new UnauthorizedException("권한이 없습니다."));
        String requestBody = "{\"id\":\"kevin\",\"pw\":\"1234\"}";

        //when
        mockMvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value("false"))
                .andExpect(jsonPath("$.error.status").value(HttpStatus.UNAUTHORIZED.value()))
                .andExpect(result -> {
                    assertThat(getApiResultExceptionClass(result)).isEqualTo(UnauthorizedException.class);
                });
    }
    // ========== HTTP 401 오류 처리 ==========

    // ========== HTTP 404 오류 처리 ==========
    @Test
    @WithMockCustomUser
    public void NotFoundException_테스트() throws Exception {

        //given
        Mockito.when(quizRestController.updateQuizState(any(),any())).thenThrow(new NotFoundException("해당하는 문제 상태를 찾을 수 없습니다."));
        String requestBody = "{\"quizStates\":\"\"}";

        //when
        mockMvc.perform(put("/api/problems/solved-check").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value("false"))
                .andExpect(jsonPath("$.error.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(result -> {
                    assertThat(getApiResultExceptionClass(result)).isEqualTo(NotFoundException.class);
                });
    }
    // ========== HTTP 404 오류 처리 ==========

    private Class<? extends Exception> getApiResultExceptionClass(MvcResult result) {
        return Objects.requireNonNull(result.getResolvedException()).getClass();
    }
}