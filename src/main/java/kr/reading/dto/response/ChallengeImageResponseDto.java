package kr.reading.dto.response;

import kr.reading.dto.ChallengeImageDto;

public record ChallengeImageResponseDto(
        Long id,
        String imgSrc,
        ChallengeResponseDto challengeResponseDto
) {

    public static ChallengeImageResponseDto from(ChallengeImageDto challengeImageDto) {
        return new ChallengeImageResponseDto(challengeImageDto.id(), challengeImageDto.imgSrc(), ChallengeResponseDto.from(challengeImageDto.challengeDto()));
    }
}
