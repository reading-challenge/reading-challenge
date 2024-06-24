package kr.reading.dto;

import kr.reading.domain.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserDto(
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

    public static UserDto of(Long id,
                             String userId,
                             String userPw,
                             String email,
                             String phone,
                             LocalDate birthday,
                             String favoriteSub,
                             String profileSrc,
                             String nickname) {
        return new UserDto(id, userId, userPw, email, phone, birthday, favoriteSub, profileSrc, nickname, null, null, null, null, null);
    }

    public static UserDto of(String userId,
                             String userPw,
                             String email,
                             String phone,
                             LocalDate birthday,
                             String favoriteSub,
                             String profileSrc,
                             String nickname) {
        return UserDto.of(null, userId, userPw, email, phone, birthday, favoriteSub, profileSrc, nickname);
    }

    public static UserDto of(String userId,
                             String userPw,
                             String email,
                             String phone,
                             LocalDate birthday,
                             String favoriteSub,
                             String nickname) {
        return UserDto.of(userId, userPw, email, phone, birthday, favoriteSub, null, nickname);
    }

    public static UserDto from(User entity) {
        return new UserDto(
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

    public User toEntity() {
        return User.of(id, userId, userPw, email, phone, birthday, favoriteSub, profileSrc, nickname);
    }

}
