package kr.reading.repository;

import kr.reading.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByUserId(String userId);

    Optional<UserAccount> findByEmail(String email);

    Optional<UserAccount> findByNickname(String nickname);

    Optional<UserAccount> findByIdAndDeletedAtIsNull(Long id);

    Optional<UserAccount> findByUserIdAndDeletedAtIsNull(String userId);
}
