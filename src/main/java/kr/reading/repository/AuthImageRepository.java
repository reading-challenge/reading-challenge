package kr.reading.repository;

import kr.reading.domain.AuthImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthImageRepository extends JpaRepository<AuthImage, Long> {
}
