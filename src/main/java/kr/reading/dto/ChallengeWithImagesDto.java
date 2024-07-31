package kr.reading.dto;

import kr.reading.domain.Challenge;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record ChallengeWithImagesDto(
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
        UserAccountDto userAccountDto,
        Set<ChallengeImageDto> challengeImageDtos
) {

    public static ChallengeWithImagesDto from(Challenge challenge) {
        return new ChallengeWithImagesDto(
                challenge.getId(),
                challenge.getSubject(),
                challenge.getTitle(),
                challenge.getIntro(),
                challenge.getDescription(),
                challenge.getRecruitedCnt(),
                challenge.getHits(),
                challenge.getStartDate(),
                challenge.getEndDate(),
                challenge.getCreatedAt(),
                challenge.getCreatedBy(),
                challenge.getModifiedAt(),
                challenge.getModifiedBy(),
                challenge.getDeletedAt(),
                UserAccountDto.from(challenge.getUserAccount()),
                challenge.getChallengeImages().stream()
                        .map(ChallengeImageDto::from)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }

}
