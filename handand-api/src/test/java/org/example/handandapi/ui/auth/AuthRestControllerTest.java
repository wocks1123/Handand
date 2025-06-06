package org.example.handandapi.ui.auth;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.handandapi.common.GlobalExceptionHandler;
import org.example.handandapi.config.TestSecurityConfig;
import org.example.handandapi.ui.auth.dto.LoginRequest;
import org.example.handandapi.ui.auth.dto.RegisterMemberWithAuthRequest;
import org.example.handanddomain.domain.auth.application.exception.LoginFailException;
import org.example.handanddomain.domain.auth.application.port.in.LoginMemberAuthUserCase;
import org.example.handanddomain.domain.auth.application.port.in.RegisterMemberWithAuthUseCase;
import org.example.handanddomain.domain.auth.application.port.in.dto.LoginResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {AuthRestController.class, GlobalExceptionHandler.class, TestSecurityConfig.class})
@DisplayName("인증 API 테스트")
class AuthRestControllerTest {

    @MockitoBean
    private RegisterMemberWithAuthUseCase registerMemberWithAuthUseCase;

    @MockitoBean
    private LoginMemberAuthUserCase loginMemberAuthUserCase;

    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    @Test
    @DisplayName("회원 가입 API 테스트")
    void test01() throws Exception {
        // given
        RegisterMemberWithAuthRequest request = new RegisterMemberWithAuthRequest(
                "TEST_USERNAME",
                "TEST_PASSWORD",
                "TEST_NAME",
                "image://TEST_PROFILE_IMAGE_URL"
        );
        given(registerMemberWithAuthUseCase.registerMemberWithAuth(any()))
                .willReturn(1L);

        // when
        var result = mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String content = result.getResponse().getContentAsString();
        Long memberId = objectMapper.readValue(content, Long.class);
        assertThat(memberId).isEqualTo(1L);

        verify(registerMemberWithAuthUseCase).registerMemberWithAuth(any());
    }

    @Nested
    @DisplayName("로그인 API 테스트")
    class LoginApiTest {

        @Test
        @DisplayName("로그인 성공")
        void test01() throws Exception {
            // given
            LoginRequest request = new LoginRequest("TEST_USERNAME", "TEST_PASSWORD");
            given(loginMemberAuthUserCase.login(any()))
                    .willReturn(new LoginResult("accessToken", "refreshToken", 1L));

            // when
            var result = mockMvc.perform(post("/api/v1/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andReturn();

            // then
            String content = result.getResponse().getContentAsString();
            LoginResult loginResult = objectMapper.readValue(content, LoginResult.class);
            assertAll(
                    () -> assertThat(loginResult.memberId()).isEqualTo(1L),
                    () -> assertThat(loginResult.accessToken()).isEqualTo("accessToken"),
                    () -> assertThat(loginResult.refreshToken()).isEqualTo("refreshToken")
            );

            verify(loginMemberAuthUserCase).login(any());
        }

        @Test
        @DisplayName("로그인 실패 - 잘못된 계정")
        void test02() throws Exception {
            // given
            LoginRequest request = new LoginRequest("INVALID_USERNAME", "INVALID_PASSWORD");
            given(loginMemberAuthUserCase.login(any()))
                    .willThrow(new LoginFailException());

            // when & then
            mockMvc.perform(post("/api/v1/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
            verify(loginMemberAuthUserCase).login(any());
        }

        @Test
        @DisplayName("로그인 실패 - 비밀번호 불일치")
        void test03() throws Exception {
            // given
            LoginRequest request = new LoginRequest("TEST_USERNAME", "WRONG_PASSWORD");
            given(loginMemberAuthUserCase.login(any()))
                    .willThrow(new LoginFailException());

            // when & then
            mockMvc.perform(post("/api/v1/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
            verify(loginMemberAuthUserCase).login(any());
        }
    }

}
