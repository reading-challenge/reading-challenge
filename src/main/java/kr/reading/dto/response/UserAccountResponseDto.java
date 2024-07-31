package kr.reading.dto.response;

import kr.reading.dto.UserAccountDto;

import java.time.LocalDate;

public record UserAccountResponseDto(
        Long id,
        String userId,
        String userPw,
        String email,
        String phone,
        LocalDate birthday,
        String favoriteSub,
        String profileSrc,
        String nickname
) {

    public static UserAccountResponseDto from(UserAccountDto userAccountDto) {
        return new UserAccountResponseDto(
                userAccountDto.id(),
                userAccountDto.userId(),
                userAccountDto.userPw(),
                userAccountDto.email(),
                userAccountDto.phone(),
                userAccountDto.birthday(),
                userAccountDto.favoriteSub(),
                userAccountDto.profileSrc(),
                userAccountDto.nickname()
        );
    }

}
