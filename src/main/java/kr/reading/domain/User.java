package kr.reading.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) private String userId; // user 계정
    @Column(nullable = false) private String userPw;  // user 패스워드
    @Column(unique = true) private String email; // 이메일
    @Column(length = 15) private String phone; // 연락처
    private LocalDate birthday; // 생년월일
    private String favoriteSub; // 관심 장르
    private String profileSrc; // 프로필 이미지
    @Column(unique = true) private String nickname; // 닉네임

    private User(Long id, String userId, String userPw, String email, String phone, LocalDate birthday,
                 String favoriteSub, String profileSrc, String nickname
    ) {
        this.id = id;
        this.userId = userId;
        this.userPw = userPw;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.favoriteSub = favoriteSub;
        this.profileSrc = profileSrc;
        this.nickname = nickname;
    }

    public static User of(Long id, String userId, String userPw, String email, String phone, LocalDate birthday,
                          String favoriteSub, String profileSrc, String nickname) {
        return new User(id, userId, userPw, email, phone, birthday, favoriteSub, profileSrc, nickname);
    }

    public void encodedPassword(String encodedPassword) {
        this.userPw = encodedPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
