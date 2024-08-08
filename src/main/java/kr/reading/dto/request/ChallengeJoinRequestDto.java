package kr.reading.dto.request;

import kr.reading.dto.ChallengeUserDto;
import kr.reading.dto.UserAccountDto;

public record ChallengeJoinRequestDto(
        Long challengeId
) {

    public ChallengeUserDto toDto(UserAccountDto userAccountDto) {
        return ChallengeUserDto.of(challengeId, userAccountDto);
    }

}
