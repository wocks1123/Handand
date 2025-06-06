package org.example.handandapi.ui.post;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.handandapi.common.GlobalExceptionHandler;
import org.example.handandapi.config.TestSecurityConfig;
import org.example.handandapi.ui.post.dto.ModifyPostRequest;
import org.example.handandapi.ui.post.dto.RegisterPostRequest;
import org.example.handandapi.ui.post.dto.RemovePostRequest;
import org.example.handanddomain.domain.member.domain.Member;
import org.example.handanddomain.domain.post.application.exception.PostNotFoundException;
import org.example.handanddomain.domain.post.application.exception.PostOwnershipException;
import org.example.handanddomain.domain.post.application.port.in.GetPostUseCase;
import org.example.handanddomain.domain.post.application.port.in.ModifyPostUseCase;
import org.example.handanddomain.domain.post.application.port.in.RegisterPostUseCase;
import org.example.handanddomain.domain.post.application.port.in.RemovePostUseCase;
import org.example.handanddomain.domain.post.domain.Post;
import org.example.handanddomain.domain.post.domain.PostStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {PostRestController.class, GlobalExceptionHandler.class, TestSecurityConfig.class})
@DisplayName("게시물 API 테스트")
class PostRestControllerTest {

    @MockitoBean
    private RegisterPostUseCase registerPostUseCase;
    @MockitoBean
    private GetPostUseCase getPostUseCase;
    @MockitoBean
    private ModifyPostUseCase modifyPostUseCase;
    @MockitoBean
    private RemovePostUseCase removePostUseCase;

    @Autowired
    MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    @Nested
    @DisplayName("게시물 등록 API 테스트")
    @WithMockUser
    class RegisterPostTest {

        @Test
        @DisplayName("게시물 등록에 성공하면 200 코드와 생성된 게시물의 고유 id를 반환한다.")
        void test01() throws Exception {
            // given
            RegisterPostRequest request = new RegisterPostRequest(1L, "Test Title", "Test Content", PostStatus.PUBLIC);
            given(registerPostUseCase.registerPost(any()))
                    .willReturn(1L);

            // when
            var result = mockMvc.perform(post("/api/v1/posts")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andReturn();

            // then
            String content = result.getResponse().getContentAsString();
            Long postId = objectMapper.readValue(content, Long.class);
            assertThat(postId).isEqualTo(1L);

            verify(registerPostUseCase).registerPost(any());
        }
    }

    @Nested
    @DisplayName("게시물 수정 API 테스트")
    @WithMockUser
    class ModifyPostTest {

        @Test
        @DisplayName("게시물 수정에 성공하면 200 코드를 반환한다.")
        void test01() throws Exception {
            // given
            long postId = 1L; // 예시 게시물 ID
            ModifyPostRequest request = new ModifyPostRequest(1L, "Updated Title", "Updated Content");
            willDoNothing()
                    .given(modifyPostUseCase)
                    .modifyPost(any());

            // when
            mockMvc.perform(put("/api/v1/posts/" + postId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());

            // then
            verify(modifyPostUseCase).modifyPost(any());
        }

        @Test
        @DisplayName("자신이 작성하지 않은 게시물을 수정하면 403 코드를 반환한다.")
        void test02() throws Exception {
            // given
            ModifyPostRequest request = new ModifyPostRequest(2L, "Updated Title", "Updated Content");
            willThrow(new PostOwnershipException())
                    .given(modifyPostUseCase)
                    .modifyPost(any());

            // when
            mockMvc.perform(put("/api/v1/posts/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isForbidden());

            // then
            verify(modifyPostUseCase).modifyPost(any());
        }

        @Test
        @DisplayName("수정하려는 게시물을 찾을 수 없으면 404 코드를 반환한다.")
        void test03() throws Exception {
            // given
            ModifyPostRequest request = new ModifyPostRequest(1L, "Updated Title", "Updated Content");
            willThrow(new PostNotFoundException(999L))
                    .given(modifyPostUseCase)
                    .modifyPost(any());

            // when & then
            mockMvc.perform(put("/api/v1/posts/999")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isNotFound());

            // then
            verify(modifyPostUseCase).modifyPost(any());
        }
    }

    @Nested
    @DisplayName("게시물 조회 API 테스트")
    @WithMockUser
    class GetPostTest {

        @Test
        @DisplayName("게시물 조회에 성공하면 200 코드와 게시물 정보를 반환한다.")
        void test01() throws Exception {
            // given
            Member dummyMember = new Member(1L, "TEST_MEMBER", null);
            Post givenPost = new Post(1L, dummyMember, "Test Title", "Test Content", PostStatus.PUBLIC);
            given(getPostUseCase.getPost(1L))
                    .willReturn(givenPost);

            // when
            var result = mockMvc.perform(get("/api/v1/posts/1"))
                    .andExpect(status().isOk())
                    .andReturn();

            // then
            String content = result.getResponse().getContentAsString();
            JsonNode responseBody = objectMapper.readTree(content);
            assertAll(
                    () -> assertThat(responseBody.get("id").asLong()).isEqualTo(givenPost.getId()),
                    () -> assertThat(responseBody.get("member").get("id").asLong()).isEqualTo(dummyMember.getId()),
                    () -> assertThat(responseBody.get("title").asText()).isEqualTo(givenPost.getTitle()),
                    () -> assertThat(responseBody.get("content").asText()).isEqualTo(givenPost.getContent()),
                    () -> assertThat(responseBody.get("status").asText()).isEqualTo(givenPost.getStatus().name())
            );

            verify(getPostUseCase).getPost(anyLong());
        }

        @Test
        @DisplayName("게시물 조회에 실패하면 404 코드를 반환한다.")
        void test02() throws Exception {
            // given
            willThrow(new PostNotFoundException(1L))
                    .given(getPostUseCase)
                    .getPost(1L);

            // then
            mockMvc.perform(get("/api/v1/posts/1"))
                    .andExpect(status().isNotFound());

            // then
            verify(getPostUseCase).getPost(1L);
        }
    }

    @Nested
    @DisplayName("게시물 삭제 API 테스트")
    @WithMockUser
    class RemovePostTest {

        @Test
        @DisplayName("게시물 삭제에 성공하면 200 코드를 반환한다.")
        void test01() throws Exception {
            // given
            RemovePostRequest request = new RemovePostRequest(1L);
            willDoNothing()
                    .given(removePostUseCase)
                    .removePost(any());

            // when
            mockMvc.perform(delete("/api/v1/posts/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());

            // then
            verify(removePostUseCase).removePost(any());
        }

        @Test
        @DisplayName("자신이 작성하지 않은 게시물을 삭제하면 403 코드를 반환한다.")
        void test02() throws Exception {
            // given
            RemovePostRequest request = new RemovePostRequest(2L);
            willThrow(new PostOwnershipException())
                    .given(removePostUseCase)
                    .removePost(any());

            // when
            mockMvc.perform(delete("/api/v1/posts/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isForbidden());

            // then
            verify(removePostUseCase).removePost(any());
        }

        @Test
        @DisplayName("삭제하려는 게시물을 찾을 수 없으면 404 코드를 반환한다.")
        void test03() throws Exception {
            // given
            RemovePostRequest request = new RemovePostRequest(1L);
            willThrow(new PostNotFoundException(999L))
                    .given(removePostUseCase)
                    .removePost(any());

            // when & then
            mockMvc.perform(delete("/api/v1/posts/999")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isNotFound());

            // then
            verify(removePostUseCase).removePost(any());
        }

    }

}
