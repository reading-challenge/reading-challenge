package kr.reading.repository;

import kr.reading.domain.ChallengeAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeAuthRepository extends JpaRepository<ChallengeAuth, Long> {
}
