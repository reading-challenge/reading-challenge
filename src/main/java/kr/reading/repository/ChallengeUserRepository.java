package kr.reading.repository;

import kr.reading.domain.ChallengeUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeUserRepository extends JpaRepository<ChallengeUser, Long> {
}
