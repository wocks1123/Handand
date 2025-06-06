package org.example.handanddomain.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    ILLEGAL_ARGUMENT("SYS_000", "잘못된 인자입니다.", 400),
    MEMBER_NOT_FOUND("MEMBER_001", "사용자를 찾을 수 없습니다.", 404),
    POST_NOT_FOUND("POST_001", "게시물을 찾을 수 없습니다.", 404),
    POST_ACCESS_DENIED("POST_002", "해당 게시물의 권한이 없습니다.", 403),

    LOGIN_FAIL("LOGIN_000", "로그인 실패", 400),
    INVALID_TOKEN("INVALID_TOKEN", "유효하지 않은 토큰입니다.", 401),

    INTERNAL_SERVER_ERROR("SYS_001", "서버 내부 오류", 500);

    private final String code;
    private final String message;
    private final int httpStatus;

    ErrorCode(String code, String message, int httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
