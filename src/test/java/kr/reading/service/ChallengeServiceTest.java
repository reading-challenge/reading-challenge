package kr.reading.service;

import kr.reading.domain.Challenge;
import kr.reading.dto.ChallengeDto;
import kr.reading.dto.UserDto;
import kr.reading.repository.ChallengeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
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
        ChallengeDto result = sut.createChallenge(challengeDto);

        // Then
        assertThat(result.id()).isEqualTo(savedChallenge.getId());
        then(challengeRepository).should().save(challenge);
    }

    private Challenge createSavedChallenge(Challenge challenge, Long id) {
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
                LocalDateTime.of(2024, 5, 22, 18, 30),
                LocalDateTime.of(2024, 10, 22, 18, 30),
                0,
                createUserDto()
        );
    }

    private UserDto createUserDto() {
        return UserDto.of(
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