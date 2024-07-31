package kr.reading.service;

import kr.reading.domain.Challenge;
import kr.reading.dto.ChallengeDto;
import kr.reading.dto.ChallengeWithImagesDto;
import kr.reading.dto.ChallengeWithImagesWithUsersWithAuthsDto;
import kr.reading.dto.UserAccountDto;
import kr.reading.global.exception.ChallengeNotFoundException;
import kr.reading.repository.ChallengeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("ChallengeService - 테스트")
@ExtendWith(MockitoExtension.class)
class ChallengeServiceTest {
    @InjectMocks private ChallengeService sut;
    @Mock private ChallengeRepository challengeRepository;

    @DisplayName("챌린지 생성 정보를 입력하면, 챌린지를 저장한다.")
    @Test
    void givenChallengeInfo_whenCreating_thenSavedChallenge() {
        // Given
        ChallengeDto challengeDto = createChallengeDto();
        Challenge challenge = challengeDto.toEntity();
        Challenge savedChallenge = createSavedChallenge(challenge, 1L);
        given(challengeRepository.save(challenge)).willReturn(savedChallenge);

        // When
        ChallengeWithImagesDto result = sut.createChallenge(challengeDto);

        // Then
        assertThat(result.id()).isEqualTo(savedChallenge.getId());
        then(challengeRepository).should().save(challenge);
    }

    @DisplayName("페이징 정보를 제공하면, 페이징 된 챌린지들을 반환한다.")
    @Test
    void givenPageInfo_whenGettingChallenges_thenReturnChallengeInfos() {
        // Given
        Pageable pageable = Pageable.ofSize(10);
        given(challengeRepository.findAllByDeletedAtIsNull(any(Pageable.class))).willReturn(Page.empty());

        // When
        Page<ChallengeWithImagesDto> result = sut.getChallenges(pageable);

        // Then
        assertThat(result).isEqualTo(Page.empty());
        then(challengeRepository).should().findAllByDeletedAtIsNull(any(Pageable.class));
    }

    @DisplayName("챌린지 ID를 제공하면, 해당 챌린지를 반환한다.")
    @Test
    void givenChallengeId_whenGettingChallenge_thenReturnChallengeInfo() {
        // Given
        Long challengeId = 1L;
        Challenge challenge = createSavedChallenge(challengeId);
        given(challengeRepository.findByIdAndDeletedAtIsNull(anyLong())).willReturn(Optional.ofNullable(challenge));

        // When
        ChallengeWithImagesWithUsersWithAuthsDto result = sut.getChallenge(challengeId);

        // Then
        assertThat(result.id()).isEqualTo(challengeId);
        then(challengeRepository).should().findByIdAndDeletedAtIsNull(anyLong());
    }

    @DisplayName("존재하지 않는 챌린지 ID를 제공하면, 예외가 발생한다.")
    @Test
    void givenChallengeId_whenGettingChallenge_thenThrowException() {
        // Given
        Long challengeId = 1L;
        given(challengeRepository.findByIdAndDeletedAtIsNull(anyLong())).willReturn(Optional.ofNullable(null));

        // When
        ChallengeNotFoundException exception = assertThrows(ChallengeNotFoundException.class,
                () -> sut.getChallenge(challengeId)
        );

        // Then
        assertThat(exception).isInstanceOf(ChallengeNotFoundException.class);
        then(challengeRepository).should().findByIdAndDeletedAtIsNull(anyLong());
    }

    @DisplayName("챌린지 업데이트와 유저 정보가 주어지면, 챌린지 정보를 업데이트 한다.")
    @Test
    void givenChallengeUpdateInfoAndUserInfo_whenUpdatingChallenge_thenReturnChallengeInfo() {
        // Given
        Long challengeId = 1L;
        ChallengeDto challengeDto = createChallengeDto();
        UserAccountDto userAccountDto = createUserDto();
        Challenge challenge = challengeDto.toEntity();
        given(challengeRepository.findByIdAndDeletedAtIsNull(anyLong())).willReturn(Optional.ofNullable(challenge));

        // When
        ChallengeWithImagesDto result = sut.updateChallenge(challengeId, challengeDto, userAccountDto);

        // Then
        assertThat(result.id()).isEqualTo(challenge.getId());
        then(challengeRepository).should().findByIdAndDeletedAtIsNull(anyLong());
    }

    @DisplayName("존재하지 않는 챌린지 정보를 업데이트 하면, 예외가 발생한다.")
    @Test
    void givenChallengeUpdateInfoAndUserInfo_whenUpdatingChallenge_thenThrowException() {
        // Given
        Long challengeId = 1L;
        ChallengeDto challengeDto = createChallengeDto();
        UserAccountDto userAccountDto = createUserDto();
        given(challengeRepository.findByIdAndDeletedAtIsNull(anyLong())).willThrow(new ChallengeNotFoundException());

        // When
        ChallengeNotFoundException exception = assertThrows(ChallengeNotFoundException.class,
                () -> sut.updateChallenge(challengeId, challengeDto, userAccountDto)
        );

        // Then
        assertThat(exception).isInstanceOf(ChallengeNotFoundException.class);
        then(challengeRepository).should().findByIdAndDeletedAtIsNull(anyLong());
    }

    @DisplayName("삭제되지 않은 챌린지 id를 제공하면, 챌린지가 삭제된다.")
    @Test
    void givenChallengeId_whenDeletingChallenge_thenDeletesChallenge() {
        // Given
        Long challengeId = 1L;
        Challenge challenge = createSavedChallenge(challengeId);
        UserAccountDto userAccountDto = createUserDto();
        given(challengeRepository.findByIdAndDeletedAtIsNull(anyLong())).willReturn(Optional.ofNullable(challenge));

        // When
        sut.deleteChallenge(challengeId, userAccountDto);

        // Then
        assertThat(challenge.getDeletedAt()).isNotNull();
        then(challengeRepository).should().findByIdAndDeletedAtIsNull(anyLong());
    }

    @DisplayName("삭제된 챌린지 id를 제공하면, 예외가 발생한다.")
    @Test
    void givenChallengeId_whenDeletingChallenge_thenThrowException() {
        // Given
        Long challengeId = 1L;
        Challenge challenge = createSavedChallenge(challengeId);
        UserAccountDto userAccountDto = createUserDto();
        given(challengeRepository.findByIdAndDeletedAtIsNull(anyLong())).willReturn(Optional.ofNullable(null));

        // When
        ChallengeNotFoundException exception = assertThrows(ChallengeNotFoundException.class,
                () -> sut.deleteChallenge(challengeId, userAccountDto)
        );

        // Then
        assertThat(exception).isInstanceOf(ChallengeNotFoundException.class);
        then(challengeRepository).should().findByIdAndDeletedAtIsNull(anyLong());
    }

    private Challenge createSavedChallenge(Challenge challenge, Long id) {
        ReflectionTestUtils.setField(challenge, "id", id);
        return challenge;
    }

    private Challenge createSavedChallenge(Long id) {
        Challenge challenge = createChallengeDto().toEntity();
        ReflectionTestUtils.setField(challenge, "id", id);
        return challenge;
    }

    private ChallengeDto createChallengeDto() {
        return ChallengeDto.of(
                1L,
                "자기계발",
                "자기계발 독서 챌린지 제목",
                "자기계발 독서 챌린지입니다.",
                "챌린지에 참여해 자기계발을 해보아요.",
                10,
                0,
                LocalDateTime.of(2024, 5, 22, 18, 30),
                LocalDateTime.of(2024, 10, 22, 18, 30),
                null,
                null,
                null,
                null,
                null,
                createUserDto()
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