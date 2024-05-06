package kr.reading.dto.response;


import kr.reading.dto.UserDto;

import java.time.LocalDate;

public record UserResponseDto(
        Long id,
        String userId,
        String phone,
        LocalDate birthday,
        String profileSrc,
        String nickname
) {

    public static UserResponseDto from(UserDto userDto) {
        return new UserResponseDto(
                userDto.id(),
                userDto.userId(),
                userDto.phone(),
                userDto.birthday(),
                userDto.profileSrc(),
                userDto.nickname()
        );
    }
}
