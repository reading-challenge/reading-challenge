package kr.reading.security;


import kr.reading.domain.UserAccount;
import kr.reading.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("UserDetailService - 테스트")
@ExtendWith(MockitoExtension.class)
class UserDetailServiceTest {
    @InjectMocks private UserDetailService sut;
    @Mock private UserRepository userRepository;

    @DisplayName("'username' 을 입력하면, 'UserDetails' 를 반환한다.")
    @Test
    void givenUsername_whenLoadUserByUsername_thenReturnsUserDetails() {
        // Given
        UserAccount userAccount = createUser();
        given(userRepository.findByUserIdAndDeletedAtIsNull(anyString())).willReturn(Optional.ofNullable(userAccount));

        // When
        UserDetails userDetails = sut.loadUserByUsername(userAccount.getUserId());

        // Then
        assertThat(userDetails.getUsername()).isEqualTo(userAccount.getUserId());
        then(userRepository).should().findByUserIdAndDeletedAtIsNull(anyString());
    }

    @DisplayName("존재하지 않거나, 탈퇴한 'username' 을 입력하면, 예외가 발생한다.")
    @Test
    void givenInactiveUsername_whenLoadUserByUsername_thenThrowsException() {
        // Given
        UserAccount userAccount = createUser();
        given(userRepository.findByUserIdAndDeletedAtIsNull(anyString())).willReturn(Optional.ofNullable(null));

        // When
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> sut.loadUserByUsername(userAccount.getUserId())
        );

        // Then
        assertThat(exception).isInstanceOf(UsernameNotFoundException.class);
        then(userRepository).should().findByUserIdAndDeletedAtIsNull(anyString());
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

        return userAccount;
    }

}