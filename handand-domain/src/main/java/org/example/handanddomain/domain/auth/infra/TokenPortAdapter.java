package org.example.handanddomain.domain.auth.infra;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.example.handanddomain.domain.auth.application.exception.InvalidTokenException;
import org.example.handanddomain.domain.auth.application.port.out.TokenPort;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
class TokenPortAdapter implements TokenPort {

    private final SecretKey secretKey;
    private final long accessTokenValidTime;
    private final long refreshTokenValidTime;

    public TokenPortAdapter(@Value("${jwt.secret-key}") String secretKey,
                            @Value("${jwt.access-token-validity-seconds:3600000}") long accessTokenValidTime,
                            @Value("${jwt.refresh-token-validity-seconds:604800000}") long refreshTokenValidTime) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.accessTokenValidTime = accessTokenValidTime;
        this.refreshTokenValidTime = refreshTokenValidTime;
    }

    @Override
    public @NotNull String generateAccessToken(@NotNull String username) {
        Claims claims = Jwts.claims()
                .subject(username)
                .add("username", username)
                .build();
        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + accessTokenValidTime);
        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(expiredAt)
                .signWith(secretKey)
                .compact();
    }

    @Override
    public @NotNull String generateRefreshToken(@NotNull String token) {
        Claims claims = parseJwtClaims(token);
        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + refreshTokenValidTime);
        return Jwts.builder()
                .subject(claims.getSubject())
                .claims(claims)
                .issuedAt(now)
                .expiration(expiredAt)
                .signWith(secretKey)
                .compact();
    }

    @Override
    public boolean isValidateToken(@Nullable String token) {
        try {
            parseJwtClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public @NotNull String extractUsername(@NotNull String token) {
        return parseJwtClaims(token)
                .get("username", String.class);
    }

    private Claims parseJwtClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException("만료된 토큰입니다.");
        } catch (MalformedJwtException e) {
            throw new InvalidTokenException("잘못된 형식의 토큰입니다.");
        } catch (SignatureException e) {
            throw new InvalidTokenException("토큰 서명이 유효하지 않습니다.");
        } catch (Exception e) {
            throw new InvalidTokenException("토큰 검증 중 오류가 발생했습니다.");
        }
    }

}
