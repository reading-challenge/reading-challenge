package kr.reading.dto;

import kr.reading.domain.ChallengeImage;

import java.time.LocalDateTime;

public record ChallengeImageDto(
        Long id,
        Long challengeId,
        String imgSrc,
        LocalDateTime createdAt,
        Long createdBy,
        LocalDateTime modifiedAt,
        Long modifiedBy,
        LocalDateTime deletedAt
) {

    public static ChallengeImageDto from(ChallengeImage challengeImage) {
        return new ChallengeImageDto(
                challengeImage.getId(),
                challengeImage.getChallenge().getId(),
                challengeImage.getImgSrc(),
                challengeImage.getCreatedAt(),
                challengeImage.getCreatedBy(),
                challengeImage.getModifiedAt(),
                challengeImage.getModifiedBy(),
                challengeImage.getDeletedAt()
        );
    }

}
