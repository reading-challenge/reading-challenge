package kr.reading.dto.response;

import kr.reading.dto.ChallengeDto;

import java.time.LocalDateTime;

public record ChallengeResponseDto(
        Long id,
        String subject,
        String title,
        String intro,
        String description,
        Integer recruitedCnt,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer hits,
        String userId,
        String nickname
) {

    public static ChallengeResponseDto from(ChallengeDto challengeDto) {
        return new ChallengeResponseDto(
                challengeDto.id(),
                challengeDto.subject(),
                challengeDto.title(),
                challengeDto.intro(),
                challengeDto.description(),
                challengeDto.recruitedCnt(),
                challengeDto.startDate(),
                challengeDto.endDate(),
                challengeDto.hits(),
                challengeDto.userDto().userId(),
                challengeDto.userDto().nickname()
        );
    }

}
