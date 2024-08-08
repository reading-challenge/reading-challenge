package kr.reading.repository;

import kr.reading.domain.Challenge;
import kr.reading.domain.ChallengeUser;
import kr.reading.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChallengeUserRepository extends JpaRepository<ChallengeUser, Long> {

    Optional<ChallengeUser> findByChallengeAndUserAccount(Challenge challenge, UserAccount userAccount);
}
