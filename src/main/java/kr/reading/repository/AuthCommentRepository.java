package kr.reading.repository;

import kr.reading.domain.AuthComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthCommentRepository extends JpaRepository<AuthComment, Long> {
}
