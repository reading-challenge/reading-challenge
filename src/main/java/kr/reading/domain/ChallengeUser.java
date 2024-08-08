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
public class ChallengeUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserAccount userAccount;

    @JoinColumn(name = "challengeId")
    @ManyToOne(optional = false)
    private Challenge challenge;

    private ChallengeUser(Long id, UserAccount userAccount, Challenge challenge) {
        this.id = id;
        this.userAccount = userAccount;
        this.challenge = challenge;
    }

    public static ChallengeUser of(Long id, UserAccount userAccount, Challenge challenge) {
        return new ChallengeUser(id, userAccount, challenge);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChallengeUser that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
