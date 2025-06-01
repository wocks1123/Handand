package org.example.handandapi.ui.member;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.handandapi.common.GlobalExceptionHandler;
import org.example.handanddomain.domain.member.application.exception.MemberNotFoundException;
import org.example.handanddomain.domain.member.application.port.in.GetMemberUseCase;
import org.example.handanddomain.domain.member.application.port.in.ModifyMemberUseCase;
import org.example.handanddomain.domain.member.application.port.in.RegisterMemberUseCase;
import org.example.handanddomain.domain.member.application.port.in.dto.ModifyMemberCommand;
import org.example.handanddomain.domain.member.application.port.in.dto.RegisterMemberCommand;
import org.example.handanddomain.domain.member.domain.Member;
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
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {MemberRestController.class, GlobalExceptionHandler.class})
@DisplayName("회원 API 테스트")
class MemberRestControllerTest {

    @MockitoBean
    private RegisterMemberUseCase registerMemberUseCase;
    @MockitoBean
    private ModifyMemberUseCase modifyMemberUseCase;
    @MockitoBean
    private GetMemberUseCase getMemberUseCase;

    @Autowired
    MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    @Nested
    @DisplayName("회원 등록 API 테스트")
    class RegisterMemberTest {

        @Test
        @DisplayName("회원 등록에 성공하면 200 코드와 생성된 회원의 고유 id를 반환한다.")
        void test01() throws Exception {
            // given
            RegisterMemberCommand command = new RegisterMemberCommand("TEST_NAME", "image://TEST_IMAGE_URL");
            given(registerMemberUseCase.registerMember(command))
                    .willReturn(1L);

            // when
            var result = mockMvc.perform(post("/api/v1/members")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\":\"TEST_NAME\", \"profileImageUrl\":\"image://TEST_IMAGE_URL\"}"))
                    .andExpect(status().isOk())
                    .andReturn();

            // then
            String content = result.getResponse().getContentAsString();
            JsonNode responseBody = objectMapper.readTree(content);
            assertThat(responseBody.asLong()).isEqualTo(1L);

            verify(registerMemberUseCase).registerMember(command);
        }
    }

    @Nested
    @DisplayName("회원 수정 API 테스트")
    class ModifyMemberTest {

        @Test
        @DisplayName("회원 프로필 이미지 URL 수정에 성공하면 200 코드를 반환한다.")
        void test01() throws Exception {
            // given
            ModifyMemberCommand command = new ModifyMemberCommand(1L, "image://TEST_IMAGE_URL");
            willDoNothing()
                    .given(modifyMemberUseCase)
                    .modifyMember(command);

            // when
            mockMvc.perform(put("/api/v1/members/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"profileImageUrl\":\"" + command.profileImageUrl() + "\"}"))
                    .andExpect(status().isOk());

            // then
            verify(modifyMemberUseCase).modifyMember(command);
        }

        @Test
        @DisplayName("등록한 회원이 아니면 404 코드를 반환한다.")
        void test02() throws Exception {
            // given
            ModifyMemberCommand command = new ModifyMemberCommand(1L, "image://TEST_IMAGE_URL");
            willThrow(new MemberNotFoundException(1L))
                    .given(modifyMemberUseCase)
                    .modifyMember(command);

            // when
            mockMvc.perform(put("/api/v1/members/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"profileImageUrl\":\"" + command.profileImageUrl() + "\"}"))
                    .andExpect(status().isNotFound());

            // then
            verify(modifyMemberUseCase).modifyMember(command);
        }
    }

    @Nested
    @DisplayName("회원 조회 API 테스트")
    class GetMemberTest {

        @Test
        @DisplayName("회원 조회에 성공하면 200 코드와 회원 정보를 반환한다.")
        void test01() throws Exception {
            // given
            Member member = new Member(1L, "TEST_NAME", "image://TEST_IMAGE_URL");
            given(getMemberUseCase.getMember(1L))
                    .willReturn(member);

            // when
            var result = mockMvc.perform(get("/api/v1/members/1"))
                    .andExpect(status().isOk())
                    .andReturn();

            // then
            String content = result.getResponse().getContentAsString();
            JsonNode responseBody = objectMapper.readTree(content);
            assertAll(
                    () -> assertThat(responseBody.get("id").asLong()).isEqualTo(member.getId()),
                    () -> assertThat(responseBody.get("name").asText()).isEqualTo(member.getName()),
                    () -> assertThat(responseBody.get("profileImageUrl").asText()).isEqualTo(member.getProfileImageUrl())
            );

            verify(getMemberUseCase).getMember(1L);
        }

        @Test
        @DisplayName("존재하지 않는 회원을 조회하면 404 코드를 반환한다.")
        void test02() throws Exception {
            // given
            willThrow(new MemberNotFoundException(1L))
                    .given(getMemberUseCase)
                    .getMember(1L);

            // when
            mockMvc.perform(get("/api/v1/members/1"))
                    .andExpect(status().isNotFound());

            // then
            verify(getMemberUseCase).getMember(1L);
        }
    }

}
