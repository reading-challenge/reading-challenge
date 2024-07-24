package kr.reading.dto;

import kr.reading.domain.UserAccount;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserAccountDto(
        Long id,
        String userId,
        String userPw,
        String email,
        String phone,
        LocalDate birthday,
        String favoriteSub,
        String profileSrc,
        String nickname,
        LocalDateTime createdAt,
        Long createdBy,
        LocalDateTime modifiedAt,
        Long modifiedBy,
        LocalDateTime deletedAt
) {

    public static UserAccountDto of(Long id,
                                    String userId,
                                    String userPw,
                                    String email,
                                    String phone,
                                    LocalDate birthday,
                                    String favoriteSub,
                                    String profileSrc,
                                    String nickname) {
        return new UserAccountDto(id, userId, userPw, email, phone, birthday, favoriteSub, profileSrc, nickname, null, null, null, null, null);
    }

    public static UserAccountDto of(String userId,
                                    String userPw,
                                    String email,
                                    String phone,
                                    LocalDate birthday,
                                    String favoriteSub,
                                    String profileSrc,
                                    String nickname) {
        return UserAccountDto.of(null, userId, userPw, email, phone, birthday, favoriteSub, profileSrc, nickname);
    }

    public static UserAccountDto of(String userId,
                                    String userPw,
                                    String email,
                                    String phone,
                                    LocalDate birthday,
                                    String favoriteSub,
                                    String nickname) {
        return UserAccountDto.of(userId, userPw, email, phone, birthday, favoriteSub, null, nickname);
    }

    public static UserAccountDto from(UserAccount entity) {
        return new UserAccountDto(
                entity.getId(),
                entity.getUserId(),
                entity.getUserPw(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getBirthday(),
                entity.getFavoriteSub(),
                entity.getProfileSrc(),
                entity.getNickname(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy(),
                entity.getDeletedAt()
        );
    }

    public UserAccount toEntity() {
        return UserAccount.of(id, userId, userPw, email, phone, birthday, favoriteSub, profileSrc, nickname);
    }

}
