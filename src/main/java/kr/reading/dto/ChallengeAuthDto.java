package kr.reading.dto;

import kr.reading.domain.ChallengeAuth;

import java.time.LocalDateTime;

public record ChallengeAuthDto(
        Long id,
        Long challengeId,
        String title,
        String content,
        LocalDateTime createdAt,
        Long createdBy,
        LocalDateTime modifiedAt,
        Long modifiedBy,
        LocalDateTime deletedAt,
        UserAccountDto userAccountDto
) {

    public static ChallengeAuthDto from(ChallengeAuth entity) {
        return new ChallengeAuthDto(
                entity.getId(),
                entity.getChallenge().getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy(),
                entity.getDeletedAt(),
                UserAccountDto.from(entity.getUserAccount())
        );
    }

}
