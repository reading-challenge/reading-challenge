package kr.reading.repository;

import kr.reading.domain.AuthCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthCommentLikeRepository extends JpaRepository<AuthCommentLike, Long> {
}
