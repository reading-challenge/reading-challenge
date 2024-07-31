package kr.reading.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import kr.reading.dto.UserAccountDto;

import java.time.LocalDate;

public record SignupRequestDto(
        @NotBlank(message = "필수 입력사항 입니다.") String userId,
        @NotBlank(message = "필수 입력사항 입니다.") String userPw,
        @Email(message = "올바른 이메일 형식이 아닙니다.") String email,
        @NotBlank(message = "필수 입력사항 입니다.") String phone,
        @Past @NotBlank(message = "필수 입력사항 입니다.") LocalDate birthday,
        String favoriteSub,
        @NotBlank(message = "필수 입력사항 입니다.") String nickname
) {

    public UserAccountDto toDto() {
        return UserAccountDto.of(userId, userPw, email, phone, birthday, favoriteSub, nickname);
    }
}