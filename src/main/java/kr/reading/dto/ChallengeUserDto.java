package kr.reading.dto;

import kr.reading.domain.ChallengeUser;

import java.time.LocalDateTime;

public record ChallengeUserDto(
        Long id,
        Long challengeId,
        LocalDateTime createdAt,
        Long createdBy,
        LocalDateTime modifiedAt,
        Long modifiedBy,
        LocalDateTime deletedAt,
        UserAccountDto userAccountDto
) {

    public static ChallengeUserDto from(ChallengeUser entity) {
        return new ChallengeUserDto(
                entity.getId(),
                entity.getChallenge().getId(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy(),
                entity.getDeletedAt(),
                UserAccountDto.from(entity.getUserAccount())
        );
    }

}
