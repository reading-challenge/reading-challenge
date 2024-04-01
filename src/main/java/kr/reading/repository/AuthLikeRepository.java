package kr.reading.repository;

import kr.reading.domain.AuthLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthLikeRepository extends JpaRepository<AuthLike, Long> {
}
