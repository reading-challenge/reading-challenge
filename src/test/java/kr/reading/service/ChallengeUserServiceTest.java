package kr.reading.service;

import kr.reading.domain.Challenge;
import kr.reading.domain.ChallengeUser;
import kr.reading.domain.UserAccount;
import kr.reading.dto.ChallengeUserDto;
import kr.reading.dto.UserAccountDto;
import kr.reading.dto.request.ChallengeUserWithChallengeDto;
import kr.reading.global.exception.AlreadyChallengeJoinException;
import kr.reading.repository.ChallengeUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("ChallengeUserService - 테스트")
@ExtendWith(MockitoExtension.class)
class ChallengeUserServiceTest {
    @InjectMocks private ChallengeUserService sut;

    @Mock private ChallengeUserRepository challengeUserRepository;
    @Mock private ChallengeService challengeService;
    @Mock private UserService userService;

    @DisplayName("챌린지 가입 정보를 입력하면, 챌린지 가입을 완료한다.")
    @Test
    void givenChallengeJoinInfo_whenJoinChallenge_thenSavedChallengeUser() {
        ChallengeUserDto challengeUserDto = createChallengeUserDto();
        Challenge challenge = createChallenge(challengeUserDto.challengeId());
        UserAccount userAccount = createUserAccount(challengeUserDto.userAccountDto().id());
        ChallengeUser challengeUser = createChallengeUser(userAccount, challenge);

        // Given
        given(challengeService.findActiveChallengeById(anyLong())).willReturn(challenge);
        given(userService.findActiveUserById(anyLong())).willReturn(userAccount);
        given(challengeUserRepository.findByChallengeAndUserAccount(challenge, userAccount))
                .willReturn(Optional.ofNullable(null));
        given(challengeUserRepository.save(any(ChallengeUser.class))).willReturn(challengeUser);

        // When
        ChallengeUserWithChallengeDto result = sut.joinChallenge(challengeUserDto);

        // Then
        assertThat(result.id()).isEqualTo(challengeUser.getId());
        then(challengeService).should().findActiveChallengeById(anyLong());
        then(userService).should().findActiveUserById(anyLong());
        then(challengeUserRepository).should().findByChallengeAndUserAccount(challenge, userAccount);
        then(challengeUserRepository).should().save(any(ChallengeUser.class));
    }

    @DisplayName("이미 가입된 회원이 챌린지 가입 정보를 입력하면, 예외가 발생한다..")
    @Test
    void givenChallengeJoinInfo_whenJoinChallenge_thenThrowsException() {
        ChallengeUserDto challengeUserDto = createChallengeUserDto();
        Challenge challenge = createChallenge(challengeUserDto.challengeId());
        UserAccount userAccount = createUserAccount(challengeUserDto.userAccountDto().id());
        ChallengeUser challengeUser = createChallengeUser(userAccount, challenge);

        // Given
        given(challengeService.findActiveChallengeById(anyLong())).willReturn(challenge);
        given(userService.findActiveUserById(anyLong())).willReturn(userAccount);
        given(challengeUserRepository.findByChallengeAndUserAccount(challenge, userAccount))
                .willReturn(Optional.ofNullable(challengeUser));

        // When
        Throwable t = catchThrowable(() -> sut.joinChallenge(challengeUserDto));

        // Then
        assertThat(t).isInstanceOf(AlreadyChallengeJoinException.class);
        then(challengeService).should().findActiveChallengeById(anyLong());
        then(userService).should().findActiveUserById(anyLong());
        then(challengeUserRepository).should().findByChallengeAndUserAccount(challenge, userAccount);
        then(challengeUserRepository).shouldHaveNoMoreInteractions();
    }

    private ChallengeUserDto createChallengeUserDto() {
        return ChallengeUserDto.of(
                1L,
                createUserDto()
        );
    }

    private ChallengeUser createChallengeUser(UserAccount userAccount, Challenge challenge) {
        return ChallengeUser.of(1L, userAccount, challenge);
    }

    private UserAccount createUserAccount(Long id) {
        return UserAccount.of(
                id,
                "user1",
                "password1",
                "user1@email.com",
                "010-1234-1234",
                LocalDate.of(2000, 4, 22),
                "자기계발",
                null,
                "닉네임"
        );
    }

    private Challenge createChallenge(Long id) {
        return Challenge.of(
                1L,
                "주제1",
                "제목1",
                "챌린지입니다.",
                "챌린지 설명입니다.",
                50,
                LocalDateTime.of(2024, 5, 22, 18, 30),
                LocalDateTime.of(2024, 10, 22, 18, 30),
                0,
                createUserAccount(2L)
        );
    }

    private UserAccountDto createUserDto() {
        return UserAccountDto.of(
                1L,
                "user1",
                "password1",
                "user1@email.com",
                "010-1234-1234",
                LocalDate.of(2000, 4, 22),
                "자기계발",
                null,
                "닉네임"
        );
    }

}