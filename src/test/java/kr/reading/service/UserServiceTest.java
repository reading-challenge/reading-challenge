package kr.reading.service;

import kr.reading.domain.UserAccount;
import kr.reading.dto.UserAccountDto;
import kr.reading.global.exception.EmailExistsException;
import kr.reading.global.exception.InactiveUserException;
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

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("UserService - 테스트")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks private UserService sut;
    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;

    @DisplayName("탈퇴하지 않은 회원 id를 입력하면, entity 반환한다.")
    @Test
    void givenId_whenFindActiveUserById_thenReturnsEntity() throws Exception {
        // Given
        UserAccount userAccount = createUser();
        given(userRepository.findByIdAndDeletedAtIsNull(anyLong())).willReturn(Optional.of(userAccount));

        // When
        UserAccount resultUser = sut.findActiveUserById(userAccount.getId());

        // Then
        assertThat(resultUser.getId()).isEqualTo(userAccount.getId());
        then(userRepository).should().findByIdAndDeletedAtIsNull(userAccount.getId());
    }

    @DisplayName("탈퇴한 회원 id를 입력하면, 예외가 발생한다.")
    @Test
    void givenInactiveId_whenFindActiveUserById_thenThrowsException() throws Exception {
        // Given
        given(userRepository.findByIdAndDeletedAtIsNull(anyLong())).willReturn(Optional.ofNullable(null));

        // When
        InactiveUserException exception = assertThrows(InactiveUserException.class,
                () -> sut.findActiveUserById(1L)
        );

        // Then
        assertThat(exception).isInstanceOf(InactiveUserException.class);
        then(userRepository).should().findByIdAndDeletedAtIsNull(1L);
    }

    @DisplayName("회원가입 정보를 입력하면, 회원가입이 완료된다.")
    @Test
    void givenUserInfo_whenSignup_thenSavedUserInfo() {
        // Given
        UserAccountDto userAccountDto = createUserDto();
        UserAccount userEntity = userAccountDto.toEntity();

        given(userRepository.findByUserId(anyString())).willReturn(Optional.ofNullable(null));
        given(userRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(null));
        given(userRepository.findByNickname(anyString())).willReturn(Optional.ofNullable(null));
        given(passwordEncoder.encode(anyString())).willReturn("encodedPassword");
        given(userRepository.save(any(UserAccount.class))).willReturn(userEntity);

        // When
        UserAccountDto savedUserAccountDto = sut.signup(userAccountDto);

        // Then
        assertThat(savedUserAccountDto.userId()).isEqualTo(userEntity.getUserId());
        then(userRepository).should().findByUserId(anyString());
        then(userRepository).should().findByNickname(anyString());
        then(passwordEncoder).should().encode(anyString());
        then(userRepository).should().save(any(UserAccount.class));
    }

    @DisplayName("중복된 userId를 입력하면, 예외가 발생한다.")
    @Test
    void givenExistsUserId_whenSignup_thenThrowException() {
        // Given
        UserAccountDto userAccountDto = createUserDto();
        UserAccount userAccount = createUser();
        given(userRepository.findByUserId(anyString())).willReturn(Optional.of(userAccount));

        // When
        UserIdExistsException exception = assertThrows(UserIdExistsException.class,
                () -> sut.signup(userAccountDto)
        );

        // Then
        assertThat(exception).isInstanceOf(UserIdExistsException.class);
        then(passwordEncoder).shouldHaveNoInteractions();
    }

    @DisplayName("중복된 이메일을 입력하면, 예외가 발생한다.")
    @Test
    void givenExistsEmail_whenSignup_thenThrowException() {
        // Given
        UserAccountDto userAccountDto = createUserDto();
        UserAccount userAccount = createUser();
        given(userRepository.findByUserId(anyString())).willReturn(Optional.ofNullable(null));
        given(userRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(userAccount));

        // When
        EmailExistsException exception = assertThrows(EmailExistsException.class,
                () -> sut.signup(userAccountDto)
        );

        // Then
        assertThat(exception).isInstanceOf(EmailExistsException.class);
        then(passwordEncoder).shouldHaveNoInteractions();
    }

    @DisplayName("중복된 닉네임을 입력하면, 예외가 발생한다.")
    @Test
    void givenExistsNickname_whenSignup_thenThrowException() {
        // Given
        UserAccountDto userAccountDto = createUserDto();
        UserAccount userAccount = createUser();
        given(userRepository.findByUserId(anyString())).willReturn(Optional.ofNullable(null));
        given(userRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(null));
        given(userRepository.findByNickname(anyString())).willReturn(Optional.ofNullable(userAccount));

        // When
        NicNameExistsException exception = assertThrows(NicNameExistsException.class,
                () -> sut.signup(userAccountDto)
        );

        // Then
        assertThat(exception).isInstanceOf(NicNameExistsException.class);
        then(passwordEncoder).shouldHaveNoInteractions();
    }

    @DisplayName("탈퇴하지 않은 회원 id를 입력하면, soft delete 한다.")
    @Test
    void givenId_whenDeleteUser_thenSoftDeleteUser() {
        // Given
        UserAccount userAccount = createUser();
        given(userRepository.findByIdAndDeletedAtIsNull(anyLong())).willReturn(Optional.ofNullable(userAccount));

        // When
        sut.deleteUser(userAccount.getId());

        // Then
        then(userRepository).should().findByIdAndDeletedAtIsNull(anyLong());
    }

    @DisplayName("탈퇴하거나 없는 회원 id를 입력하면, 예외를 발생한다.")
    @Test
    void givenInactiveId_whenDeleteUser_thenThrowsException() {
        // Given
        given(userRepository.findByIdAndDeletedAtIsNull(anyLong())).willReturn(Optional.ofNullable(null));

        // When
        InactiveUserException exception = assertThrows(InactiveUserException.class,
                () -> sut.deleteUser(1L)
        );

        // Then
        assertThat(exception).isInstanceOf(InactiveUserException.class);
        then(userRepository).should().findByIdAndDeletedAtIsNull(anyLong());
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

    private UserAccountDto createUserDto() {
        UserAccountDto userAccountDto = UserAccountDto.of(
                "user1",
                "password1",
                "user1@email.com",
                "010-1234-1234",
                LocalDate.of(2000, 4, 22),
                "자기계발",
                null,
                "닉네임1"
        );

        return userAccountDto;
    }
}