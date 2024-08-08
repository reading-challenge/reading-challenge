package kr.reading.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserAccount userAccount;

    @ToString.Exclude
    @OneToMany(mappedBy = "challenge")
    private Set<ChallengeAuth> challengeAuths = new LinkedHashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "challenge")
    private Set<ChallengeImage> challengeImages = new LinkedHashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "challenge")
    private Set<ChallengeUser> challengeUsers = new LinkedHashSet<>();

    private Challenge(Long id,
                      String subject,
                      String title,
                      String intro,
                      String description,
                      Integer recruitedCnt,
                      LocalDateTime startDate,
                      LocalDateTime endDate,
                      Integer hits,
                      UserAccount userAccount
    ) {
        this.id = id;
        this.subject = subject;
        this.title = title;
        this.intro = intro;
        this.description = description;
        this.recruitedCnt = recruitedCnt;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hits = hits;
        this.userAccount = userAccount;
    }

    public void update(String subject, String title, String intro,
                       String description, Integer integer,
                       LocalDateTime startDate, LocalDateTime endDate
    ) {
        this.subject = subject;
        this.title = title;
        this.intro = intro;
        this.description = description;
        this.recruitedCnt = integer;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Challenge of(Long id,
                               String subject,
                               String title,
                               String intro,
                               String description,
                               Integer recruitedCnt,
                               LocalDateTime startDate,
                               LocalDateTime endDate,
                               Integer hits,
                               UserAccount userAccount
    ) {
        return new Challenge(
                id,
                subject,
                title,
                intro,
                description,
                recruitedCnt,
                startDate,
                endDate,
                hits,
                userAccount
        );
    }

    @PrePersist
    protected void onCreate() {
        this.hits = 0; // 저장하기 전 조회 수 0 으로 설정
    }

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
