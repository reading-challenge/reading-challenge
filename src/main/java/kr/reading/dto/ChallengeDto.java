package kr.reading.dto;

import kr.reading.domain.Challenge;

import java.time.LocalDateTime;

public record ChallengeDto(
        Long id,
        String subject,
        String title,
        String intro,
        String description,
        Integer recruitedCnt,
        Integer hits,
        LocalDateTime startDate,
        LocalDateTime endDate,
        LocalDateTime createdAt,
        Long createdBy,
        LocalDateTime modifiedAt,
        Long modifiedBy,
        LocalDateTime deletedAt,
        UserAccountDto userAccountDto
) {

    public static ChallengeDto of(
            Long id,
            String subject,
            String title,
            String intro,
            String description,
            Integer recruitedCnt,
            Integer hits,
            LocalDateTime startDate,
            LocalDateTime endDate,
            LocalDateTime createdAt,
            Long createdBy,
            LocalDateTime modifiedAt,
            Long modifiedBy,
            LocalDateTime deletedAt,
            UserAccountDto userAccountDto
    ) {
        return new ChallengeDto(
                id,
                subject,
                title,
                intro,
                description,
                recruitedCnt,
                hits,
                startDate,
                endDate,
                createdAt,
                createdBy,
                modifiedAt,
                modifiedBy,
                deletedAt,
                userAccountDto
        );
    }

    public static ChallengeDto of(
            String subject,
            String title,
            String intro,
            String description,
            Integer recruitedCnt,
            LocalDateTime startDate,
            LocalDateTime endDate,
            UserAccountDto userAccountDto
    ) {
        return ChallengeDto.of(
                null,
                subject,
                title,
                intro,
                description,
                recruitedCnt,
                null,
                startDate,
                endDate,
                null,
                null,
                null,
                null,
                null,
                userAccountDto
        );
    }

    public static ChallengeDto of(
            String subject,
            String title,
            String intro,
            String description,
            Integer recruitedCnt,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        return ChallengeDto.of(
                subject,
                title,
                intro,
                description,
                recruitedCnt,
                startDate,
                endDate,
                null
        );
    }

    public Challenge toEntity() {
        return Challenge.of(
                id,
                subject,
                title,
                intro,
                description,
                recruitedCnt,
                startDate,
                endDate,
                hits,
                userAccountDto.toEntity()
        );
    }

    public static ChallengeDto from(Challenge entity) {
        return ChallengeDto.of(
                entity.getId(),
                entity.getSubject(),
                entity.getTitle(),
                entity.getIntro(),
                entity.getDescription(),
                entity.getRecruitedCnt(),
                entity.getHits(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy(),
                entity.getDeletedAt(),
                UserAccountDto.from(entity.getUserAccount())
        );
    }

}
