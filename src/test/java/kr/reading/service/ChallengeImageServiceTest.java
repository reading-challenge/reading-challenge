package kr.reading.service;


import kr.reading.domain.Challenge;
import kr.reading.domain.ChallengeImage;
import kr.reading.domain.UserAccount;
import kr.reading.dto.ChallengeImageDto;
import kr.reading.global.exception.FileSavedException;
import kr.reading.repository.ChallengeImageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("ChallengeImageService - 테스트")
@ExtendWith(MockitoExtension.class)
class ChallengeImageServiceTest {
    @InjectMocks private ChallengeImageService sut;
    @Mock private ChallengeImageRepository challengeImageRepository;
    @Mock private FileService fileService;
    @Mock private ChallengeService challengeService;

    @DisplayName("챌린지 id와 챌린지 이미지를 입력하면, 챌린지 이미지를 저장한다.")
    @Test
    void givenChallengeIdAndChallengeImages_whenCreating_thenSavedChallengeImages() {
        // Given
        Long challengeId = 1L;
        Challenge challenge = createChallenge(challengeId);
        ChallengeImage savedChallenge = createChallengeImage(challenge);
        List<MultipartFile> images = List.of(new MockMultipartFile("images", "image1.jpg", "image/jpeg", "image1".getBytes()));
        String imgUrl = "image/challenge-image.jpg";
        given(challengeService.findActiveChallengeById(anyLong())).willReturn(challenge);
        given(fileService.saveImage(any(MultipartFile.class))).willReturn(imgUrl);
        given(challengeImageRepository.save(any(ChallengeImage.class))).willReturn(savedChallenge);

        // When
        sut.createChallengeImage(challengeId, images);

        // Then
        then(challengeService).should().findActiveChallengeById(anyLong());
        then(fileService).should().saveImage(any(MultipartFile.class));
        then(challengeImageRepository).should().save(any(ChallengeImage.class));
    }

    @DisplayName("챌린지 이미지 저장에 실패하면, 예외가 발생한다.")
    @Test
    void givenChallengeIdAndChallengeImages_whenCreating_thenThrowException() {
        // Given
        Long challengeId = 1L;
        Challenge challenge = createChallenge(challengeId);
        ChallengeImage savedChallenge = createChallengeImage(challenge);
        List<MultipartFile> images = List.of(new MockMultipartFile("images", "image1.jpg", "image/jpeg", "image1".getBytes()));
        given(challengeService.findActiveChallengeById(anyLong())).willReturn(challenge);
        given(fileService.saveImage(any(MultipartFile.class))).willThrow(FileSavedException.class);

        // When
        Throwable t = catchThrowable(() -> sut.createChallengeImage(challengeId, images));

        // Then
        assertThat(t).isInstanceOf(FileSavedException.class);
        then(challengeService).should().findActiveChallengeById(anyLong());
        then(fileService).should().saveImage(any(MultipartFile.class));
    }

    private Challenge createChallenge(Long challengeId) {
        return Challenge.of(
                challengeId,
                "주제1",
                "제목1",
                "챌린지입니다.",
                "챌린지 설명입니다.",
                50,
                LocalDateTime.of(2024, 5, 22, 18, 30),
                LocalDateTime.of(2024, 10, 22, 18, 30),
                0,
                createUserAccount()
        );
    }

    private ChallengeImage createChallengeImage(Challenge challenge) {
        return ChallengeImage.of(
                "image/challenge-image.jpg",
                challenge
        );
    }

    private UserAccount createUserAccount() {
        return UserAccount.of(
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