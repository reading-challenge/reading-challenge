package kr.reading.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)
@Entity
public class Challenge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject; // 챌린지 주제
    private String title; // 제목
    private String intro; // 소개
    private String description; // 설명
    private Integer recruitedCnt; // 모집인원
    private LocalDateTime startDate; // 시작일
    private LocalDateTime endDate; // 종료일
    private Integer hits; // 조회수

    @JoinColumn(name = "userId")
    @ManyToOne(optional = false)
    private User user;

    @ToString.Exclude
    @OneToMany(mappedBy = "challenge")
    private Set<ChallengeAuth> challengeAuths = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Challenge that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
