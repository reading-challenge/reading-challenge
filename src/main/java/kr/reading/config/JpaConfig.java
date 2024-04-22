package kr.reading.config;

import kr.reading.security.PrincipalDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {
    private static final Long DEFAULT_USER_ID = 0L; // 미 인증 시 사용되는 ID 값

    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(authentication -> authentication != null && authentication.isAuthenticated())
                .map(authentication -> {
                    if (authentication.getPrincipal() instanceof PrincipalDetails principalDetails) {
                        return principalDetails.id();
                    }

                    return DEFAULT_USER_ID;
                });
    }
}
