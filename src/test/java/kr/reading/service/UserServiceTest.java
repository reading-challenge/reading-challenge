package kr.reading.service;

import kr.reading.domain.User;
import kr.reading.dto.UserDto;
import kr.reading.global.exception.EmailExistsException;
import kr.reading.global.exception.NicNameExistsException;
import kr.reading.global.exception.UserIdExistsException;
import kr.reading.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("UserService - 테스트")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks private UserService sut;
    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;

    @DisplayName("회원가입 정보를 입력하면, 회원가입이 완료된다.")
    @Test
    void givenUserInfo_whenSignup_thenSavedUserInfo() {
        // Given
        UserDto userDto = createUserDto();
        User userEntity = userDto.toEntity();

        given(userRepository.findByUserId(anyString())).willReturn(Optional.ofNullable(null));
        given(userRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(null));
        given(userRepository.findByNickname(anyString())).willReturn(Optional.ofNullable(null));
        given(passwordEncoder.encode(anyString())).willReturn("encodedPassword");
        given(userRepository.save(any(User.class))).willReturn(userEntity);

        // When
        UserDto savedUserDto = sut.signup(userDto);

        // Then
        assertThat(savedUserDto.userId()).isEqualTo(userEntity.getUserId());
        then(userRepository).should().findByUserId(anyString());
        then(userRepository).should().findByNickname(anyString());
        then(passwordEncoder).should().encode(anyString());
        then(userRepository).should().save(any(User.class));
    }

    @DisplayName("중복된 userId를 입력하면, 예외가 발생한다.")
    @Test
    void givenExistsUserId_whenSignup_thenThrowException() {
        // Given
        UserDto userDto = createUserDto();
        given(userRepository.findByUserId(anyString())).willThrow(new UserIdExistsException());

        // When
        UserIdExistsException exception = assertThrows(UserIdExistsException.class,
                () -> sut.signup(userDto)
        );

        // Then
        assertThat(exception).isInstanceOf(UserIdExistsException.class);
        then(passwordEncoder).shouldHaveNoInteractions();
    }

    @DisplayName("중복된 이메일을 입력하면, 예외가 발생한다.")
    @Test
    void givenExistsEmail_whenSignup_thenThrowException() {
        // Given
        UserDto userDto = createUserDto();
        given(userRepository.findByUserId(anyString())).willReturn(Optional.ofNullable(null));
        given(userRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(null));
        given(userRepository.findByNickname(anyString())).willThrow(new NicNameExistsException());

        // When
        NicNameExistsException exception = assertThrows(NicNameExistsException.class,
                () -> sut.signup(userDto)
        );

        // Then
        assertThat(exception).isInstanceOf(NicNameExistsException.class);
        then(passwordEncoder).shouldHaveNoInteractions();
    }

    @DisplayName("중복된 닉네임을 입력하면, 예외가 발생한다.")
    @Test
    void givenExistsNickname_whenSignup_thenThrowException() {
        // Given
        UserDto userDto = createUserDto();
        given(userRepository.findByUserId(anyString())).willReturn(Optional.ofNullable(null));
        given(userRepository.findByEmail(anyString())).willThrow(new EmailExistsException());

        // When
        EmailExistsException exception = assertThrows(EmailExistsException.class,
                () -> sut.signup(userDto)
        );

        // Then
        assertThat(exception).isInstanceOf(EmailExistsException.class);
        then(passwordEncoder).shouldHaveNoInteractions();
    }

    private User createUser() {
        User user = User.of(
                "user1",
                "password1",
                "user1@email.com",
                "010-1234-1234",
                LocalDate.of(2000, 4, 22),
                "자기계발",
                null,
                "닉네임1"
        );

        ReflectionTestUtils.setField(user, "id", 1L);

        return user;
    }

    private UserDto createUserDto() {
        UserDto userDto = UserDto.of(
                "user1",
                "password1",
                "user1@email.com",
                "010-1234-1234",
                LocalDate.of(2000, 4, 22),
                "자기계발",
                null,
                "닉네임1"
        );

        return userDto;
    }
}