package kr.reading.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)
@Entity
public class ChallengeAuth extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // 챌린지 인증 제목
    private String content; // 챌린지 인증 내용

    @JoinColumn(name = "userId")
    @ManyToOne(optional = false)
    private User user;

    @JoinColumn(name = "challengeId")
    @ManyToOne(optional = false)
    private Challenge challenge;

    @ToString.Exclude
    @OneToMany(mappedBy = "challengeAuth")
    private Set<AuthComment> authComments = new LinkedHashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "challengeAuth")
    private Set<AuthLike> authLikes = new LinkedHashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "challengeAuth")
    private Set<AuthImage> authImages = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChallengeAuth that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
