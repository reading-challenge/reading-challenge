package kr.reading.dto.request;

import kr.reading.domain.ChallengeUser;
import kr.reading.dto.ChallengeDto;
import kr.reading.dto.UserAccountDto;

import java.time.LocalDateTime;

public record ChallengeUserWithChallengeDto(
        Long id,
        LocalDateTime createdAt,
        Long createdBy,
        LocalDateTime modifiedAt,
        Long modifiedBy,
        LocalDateTime deletedAt, // 삭제날
        ChallengeDto challengeDto, // 챌린지
        UserAccountDto userAccountDto // 챌린지 회원
) {
    public static ChallengeUserWithChallengeDto from(ChallengeUser entity) {
        return new ChallengeUserWithChallengeDto(
                entity.getId(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy(),
                entity.getDeletedAt(),
                ChallengeDto.from(entity.getChallenge()),
                UserAccountDto.from(entity.getUserAccount())
        );
    }

}
