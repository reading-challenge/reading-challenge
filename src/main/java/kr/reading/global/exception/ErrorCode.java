package kr.reading.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {


    // 5xx - 서버 에러
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러"),

    // USER
    USERID_EXISTS(HttpStatus.BAD_REQUEST, "이미 등록된 유저 ID 입니다."),
    EMAIL_EXISTS(HttpStatus.BAD_REQUEST, "이미 등록된 이메일 입니다."),
    NICKNAME_EXISTS(HttpStatus.BAD_REQUEST, "이미 등록된 닉네임 입니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 유저를 찾을 수 없습니다."),
    INACTIVE_USER(HttpStatus.BAD_REQUEST, "이미 탈퇴 되었거나 가입된 유저가 아닙니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
