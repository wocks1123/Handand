package org.example.handanddomain.domain.auth.infra;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.example.handanddomain.domain.auth.application.exception.InvalidTokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TokenPortAdapterTest {

    private TokenPortAdapter tokenPortAdapter;

    private static String SECRET_KEY = "8ed73ae769c14aa2946bba8766233a6417ca3d6a29e89a94fd01b5968a87795620011eef780aaff1938b24bac683a9edfe8e5af30f7eab5116873514b5234a2b";

    @BeforeEach
    void setup() {
        tokenPortAdapter = new TokenPortAdapter(SECRET_KEY, 10_000, 60_000);
    }

    @Test
    @DisplayName("액세스 토큰 생성한다.")
    void test01() {
        // given
        String username = "TEST_USERNAME";

        // when
        String accessToken = tokenPortAdapter.generateAccessToken(username);

        // then
        assertThat(accessToken).isNotNull();
        assertAll(
                () -> assertThat(tokenPortAdapter.isValidateToken(accessToken)).isTrue(),
                () -> assertThat(tokenPortAdapter.extractUsername(accessToken)).isEqualTo(username)
        );
    }

    @Test
    @DisplayName("리프레시 토큰을 생성한다.")
    void test02() {
        // given
        String username = "TEST_USERNAME";
        String accessToken = tokenPortAdapter.generateAccessToken(username);

        // when
        String refreshToken = tokenPortAdapter.generateRefreshToken(accessToken);

        // then
        assertThat(refreshToken).isNotNull();
        assertAll(
                () -> assertThat(tokenPortAdapter.isValidateToken(refreshToken)).isTrue(),
                () -> assertThat(tokenPortAdapter.extractUsername(refreshToken)).isEqualTo(username)
        );
    }

    @Test
    @DisplayName("토큰 유효성 검사에 성공한다.")
    void test03() {
        // given
        String accessToken = tokenPortAdapter.generateAccessToken("TEST_USERNAME");

        // when & then
        assertThat(tokenPortAdapter.isValidateToken(accessToken)).isTrue();
    }

    @Test
    @DisplayName("유효하지 않은 토큰에 대해 유효성 검사에 실패한다.")
    void test04() {
        // given
        String invalidToken = "invalid.jwt.token";

        // when & then
        assertThat(tokenPortAdapter.isValidateToken(invalidToken)).isFalse();
    }

    @Nested
    @DisplayName("토큰 파싱 테스트")
    class ParseTokenTest {

        @Test
        @DisplayName("만료된 토큰을 파싱하면 InvalidTokenException이 발생한다")
        void test01() {
            // given
            Date issuedAt = new Date(System.currentTimeMillis() - 10000L);
            Date expiredAt = new Date(System.currentTimeMillis() - 5000L);
            String expiredToken = Jwts.builder()
                    .subject("subject")
                    .issuedAt(issuedAt)
                    .expiration(expiredAt)
                    .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                    .compact();

            // when
            InvalidTokenException exception = assertThrows(InvalidTokenException.class, () -> {
                invokeParseJwtClaims(expiredToken);
            });

            // then
            assertThat(exception.getErrorMessage()).contains("만료된 토큰입니다.");
        }

        @Test
        @DisplayName("잘못된 형식의 토큰을 파싱하면 InvalidTokenException이 발생한다.")
        void test02() {
            // given
            String malformedToken = "invalid.jwt.token";

            // when & then
            InvalidTokenException exception = assertThrows(InvalidTokenException.class, () -> {
                invokeParseJwtClaims(malformedToken);
            });

            // then
            assertThat(exception.getErrorMessage()).contains("잘못된 형식의 토큰입니다.");
        }

        @Test
        @DisplayName("잘못된 시그니처의 토큰을 파싱하면 InvalidTokenException이 발생한다")
        void test03() {
            // given
            SecretKey wrongKey = Keys.hmacShaKeyFor("wrongSecretKeyForTestingPurposesThatIsLongEnough".getBytes());
            String tokenWithWrongSignature = Jwts.builder()
                    .subject("subject")
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 100_000L))
                    .signWith(wrongKey)
                    .compact();

            // when
            InvalidTokenException exception = assertThrows(InvalidTokenException.class, () -> {
                invokeParseJwtClaims(tokenWithWrongSignature);
            });

            // then
            assertThat(exception.getErrorMessage()).contains("토큰 서명이 유효하지 않습니다.");
        }

        @ParameterizedTest
        @DisplayName("null인 토큰을 파싱하면 InvalidTokenException이 발생한다")
        @NullAndEmptySource
        void test04(String nullOrEmptyToken) {
            // when
            InvalidTokenException exception = assertThrows(InvalidTokenException.class, () -> {
                invokeParseJwtClaims(nullOrEmptyToken);
            });

            // then
            assertThat(exception.getErrorMessage()).contains("토큰 검증 중 오류가 발생했습니다.");
        }

        // private 메서드 테스트를 위한 리플렉션
        private Claims invokeParseJwtClaims(String token) {
            return ReflectionTestUtils.invokeMethod(tokenPortAdapter, "parseJwtClaims", token);
        }
    }

}
