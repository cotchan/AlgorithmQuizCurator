package com.yhcy.aqc.controller.user;

import com.yhcy.aqc.controller.UserRestController;
import com.yhcy.aqc.security.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRestControllerTest {

    private final MockMvc mockMvc;

    @Autowired
    public UserRestControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @WithMockCustomUser
    @DisplayName("내 정보 조회 성공 테스트 (jwt 토큰이 올바른 경우)")
    void meSuccessTest() throws Exception {
        ResultActions result = mockMvc.perform(
                get("/api/user/me")
                        .accept(MediaType.APPLICATION_JSON)
        );

        /**
         *     private Integer seq;
         *     private String userId;
         *     private String nickName;
         */
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(UserRestController.class))
                .andExpect(handler().methodName("me"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.response.seq", is(23)))
                .andExpect(jsonPath("$.response.userId", is("zoroKevin")))
                .andExpect(jsonPath("$.response.nickName", is("test001")))
        ;
    }
}