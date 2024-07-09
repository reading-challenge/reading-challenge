package kr.reading.config;

import kr.reading.domain.UserAccount;
import kr.reading.repository.UserRepository;
import kr.reading.security.UserDetailService;
import kr.reading.security.handler.CustomAccessDeniedHandler;
import kr.reading.security.handler.CustomAuthenticationFailureHandler;
import kr.reading.security.handler.CustomAuthenticationSuccessHandler;
import kr.reading.security.handler.CustomLoginAuthenticationEntryPoint;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(
        {
                SecurityConfig.class,
                CustomAuthenticationSuccessHandler.class,
                CustomAuthenticationFailureHandler.class,
                AuthenticationConfiguration.class,
                CustomLoginAuthenticationEntryPoint.class,
                CustomAccessDeniedHandler.class,
                UserDetailService.class
        }
)
public class TestSecurityConfig {
    @MockBean private UserRepository userRepository;

    @BeforeTestMethod
    void securitySetUp() {
        UserAccount userAccount = createUser();

        given(userRepository.findByUserIdAndDeletedAtIsNull(anyString()))
                .willReturn(Optional.of(userAccount));
    }

    private UserAccount createUser() {
        UserAccount userAccount = UserAccount.of(
                1L,
                "user1",
                "password1",
                "user1@email.com",
                "010-1234-1234",
                LocalDate.of(2000, 4, 22),
                "자기계발",
                null,
                "닉네임1"
        );

        ReflectionTestUtils.setField(userAccount, "id", 1L);

        return userAccount;
    }
}
