package kr.reading.repository;

import kr.reading.domain.Challenge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    Page<Challenge> findAllByDeletedAtIsNull(Pageable pageable);
}
