package kr.reading.dto.response;

import kr.reading.dto.UserDto;

import java.time.LocalDateTime;

public record DeleteUserResponseDto(
        String userId,
        LocalDateTime deletedAt
) {

    public static DeleteUserResponseDto from(UserDto userDto) {
        return new DeleteUserResponseDto(
                userDto.userId(),
                userDto.deletedAt()
        );
    }
}
