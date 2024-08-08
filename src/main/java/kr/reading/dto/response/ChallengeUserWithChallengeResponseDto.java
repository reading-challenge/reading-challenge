package kr.reading.dto.response;

import kr.reading.dto.request.ChallengeUserWithChallengeDto;

import java.time.LocalDateTime;

public record ChallengeUserWithChallengeResponseDto(
        Long id, // 챌린지 id
        String challengeUser, // 챌린지 회원 닉네임,
        ChallengeResponseDto challengeResponseDto // 챌린지 응답
) {

    private record ChallengeResponseDto(
            Long id,
            String subject,
            String title,
            String intro,
            String description,
            Integer recruitedCnt,
            Integer hits,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String creator
    ) {
    }

    public static ChallengeUserWithChallengeResponseDto from(ChallengeUserWithChallengeDto challengeUserWithChallengeDto) {
        return new ChallengeUserWithChallengeResponseDto(
                challengeUserWithChallengeDto.id(),
                challengeUserWithChallengeDto.userAccountDto().nickname(),
                new ChallengeResponseDto(
                        challengeUserWithChallengeDto.challengeDto().id(),
                        challengeUserWithChallengeDto.challengeDto().subject(),
                        challengeUserWithChallengeDto.challengeDto().title(),
                        challengeUserWithChallengeDto.challengeDto().intro(),
                        challengeUserWithChallengeDto.challengeDto().description(),
                        challengeUserWithChallengeDto.challengeDto().recruitedCnt(),
                        challengeUserWithChallengeDto.challengeDto().hits(),
                        challengeUserWithChallengeDto.challengeDto().startDate(),
                        challengeUserWithChallengeDto.challengeDto().endDate(),
                        challengeUserWithChallengeDto.challengeDto().userAccountDto().nickname()
                )
        );
    }

}
