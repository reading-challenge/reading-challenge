package kr.reading.dto;

import kr.reading.domain.Challenge;
import kr.reading.domain.ChallengeUser;
import kr.reading.domain.UserAccount;

import java.time.LocalDateTime;

public record ChallengeUserDto(
        Long id,
        LocalDateTime createdAt,
        Long createdBy,
        LocalDateTime modifiedAt,
        Long modifiedBy,
        LocalDateTime deletedAt, // 삭제날
        Long challengeId, // 챌린지 id
        UserAccountDto userAccountDto // 챌린지 회원
) {

    public static ChallengeUserDto of(Long id,
                                      LocalDateTime createdAt,
                                      Long createdBy,
                                      LocalDateTime modifiedAt,
                                      Long modifiedBy,
                                      LocalDateTime deletedAt,
                                      Long challengeId,
                                      UserAccountDto userAccountDto
    ) {
        return new ChallengeUserDto(id, createdAt, createdBy, modifiedAt, modifiedBy, deletedAt, challengeId, userAccountDto);
    }

    public static ChallengeUserDto of(Long challengeId, UserAccountDto userAccountDto) {
        return new ChallengeUserDto(null, null, null, null, null, null, challengeId, userAccountDto);
    }


    public static ChallengeUserDto from(ChallengeUser entity) {
        return new ChallengeUserDto(
                entity.getId(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy(),
                entity.getDeletedAt(),
                entity.getChallenge().getId(),
                UserAccountDto.from(entity.getUserAccount())
        );
    }


    public ChallengeUser toEntity(Challenge challenge, UserAccount userAccount) {
        return ChallengeUser.of(id, userAccount, challenge);
    }

}
