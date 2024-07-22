package kr.reading.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChallengeImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgSrc; // 이미지 경로

    @JoinColumn(name = "challengeId")
    @ManyToOne(optional = false)
    private Challenge challenge;

    private ChallengeImage(String imgSrc, Challenge challenge) {
        this.imgSrc = imgSrc;
        this.challenge = challenge;
    }

    public static ChallengeImage of(String imgSrc, Challenge challenge) {
        return new ChallengeImage(imgSrc, challenge);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChallengeImage that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

}
