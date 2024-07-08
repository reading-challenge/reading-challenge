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
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer hits,
        LocalDateTime createdAt,
        Long createdBy,
        LocalDateTime modifiedAt,
        Long modifiedBy,
        LocalDateTime deletedAt,
        UserDto userDto
) {

    public static ChallengeDto of(Long id,
                                  String subject,
                                  String title,
                                  String intro,
                                  String description,
                                  Integer recruitedCnt,
                                  LocalDateTime startDate,
                                  LocalDateTime endDate,
                                  Integer hits,
                                  LocalDateTime createdAt,
                                  Long createdBy,
                                  LocalDateTime modifiedAt,
                                  Long modifiedBy,
                                  LocalDateTime deletedAt,
                                  UserDto userDto
    ) {
        return new ChallengeDto(
                id,
                subject,
                title,
                intro,
                description,
                recruitedCnt,
                startDate,
                endDate,
                hits,
                createdAt,
                createdBy,
                modifiedAt,
                modifiedBy,
                deletedAt,
                userDto
        );
    }

    public static ChallengeDto of(String subject,
                                  String title,
                                  String intro,
                                  String description,
                                  Integer recruitedCnt,
                                  LocalDateTime startDate,
                                  LocalDateTime endDate,
                                  UserDto userDto
    ) {
        return ChallengeDto.of(
                null,
                subject,
                title,
                intro,
                description,
                recruitedCnt,
                startDate,
                endDate,
                null,
                null,
                null,
                null,
                null,
                null,
                userDto
        );
    }

    public static ChallengeDto of(String subject,
                                  String title,
                                  String intro,
                                  String description,
                                  Integer recruitedCnt,
                                  LocalDateTime startDate,
                                  LocalDateTime endDate
    ) {
        return ChallengeDto.of(
                null,
                subject,
                title,
                intro,
                description,
                recruitedCnt,
                startDate,
                endDate,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static ChallengeDto of(Long id,
                                  String subject,
                                  String title,
                                  String intro,
                                  String description,
                                  Integer recruitedCnt,
                                  LocalDateTime startDate,
                                  LocalDateTime endDate,
                                  Integer hits,
                                  UserDto userDto
    ) {
        return ChallengeDto.of(
                id,
                subject,
                title,
                intro,
                description,
                recruitedCnt,
                startDate,
                endDate,
                hits,
                null,
                null,
                null,
                null,
                null,
                userDto
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
                userDto.toEntity()
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
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getHits(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy(),
                entity.getDeletedAt(),
                UserDto.from(entity.getUserAccount())
        );
    }

}
