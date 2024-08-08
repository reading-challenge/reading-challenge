package kr.reading.controller;

import kr.reading.config.JsonDataEncoder;
import kr.reading.config.TestSecurityConfig;
import kr.reading.dto.ChallengeDto;
import kr.reading.dto.ChallengeUserDto;
import kr.reading.dto.UserAccountDto;
import kr.reading.dto.request.ChallengeJoinRequestDto;
import kr.reading.dto.request.ChallengeUserWithChallengeDto;
import kr.reading.global.exception.AlreadyChallengeJoinException;
import kr.reading.service.ChallengeUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ChallengeUserController - 테스트")
@Import({TestSecurityConfig.class, JsonDataEncoder.class})
@WebMvcTest(ChallengeUserController.class)
class ChallengeUserControllerTest {

    private static final String CHALLENGE_ALREADY_JOIN_MSG = "이미 가입된 챌린지 입니다.";

    @Autowired private MockMvc mvc;
    @Autowired private JsonDataEncoder jsonDataEncoder;

    @MockBean private ChallengeUserService challengeUserService;

    @WithUserDetails(value = "user1", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("챌린지 가입 - 성공")
    @Test
    void giveChallengeJoinInfo_whenJoinChallenge_thenSucceeded() throws Exception {
        // Given
        ChallengeJoinRequestDto challengeJoinRequestDto = createChallengeJoinRequestDto();
        ChallengeUserWithChallengeDto challengeUserWithChallengeDto = createChallengeUserWithChallengeDto(createChallengeDto(), createUserDto());
        given(challengeUserService.joinChallenge(any(ChallengeUserDto.class))).willReturn(challengeUserWithChallengeDto);

        // When & Then
        mvc.perform(post("/challenge-users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonDataEncoder.encode(challengeJoinRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.message").isEmpty());
        then(challengeUserService).should().joinChallenge(any(ChallengeUserDto.class));
    }

    @WithUserDetails(value = "user1", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("챌린지 가입 - 실패")
    @Test
    void giveChallengeJoinInfo_whenJoinChallenge_thenFailed() throws Exception {
        // Given
        ChallengeJoinRequestDto challengeJoinRequestDto = createChallengeJoinRequestDto();
        given(challengeUserService.joinChallenge(any(ChallengeUserDto.class))).willThrow(new AlreadyChallengeJoinException());

        // When & Then
        mvc.perform(post("/challenge-users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonDataEncoder.encode(challengeJoinRequestDto)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.message").value(CHALLENGE_ALREADY_JOIN_MSG));
        then(challengeUserService).should().joinChallenge(any(ChallengeUserDto.class));
    }

    private ChallengeUserWithChallengeDto createChallengeUserWithChallengeDto(ChallengeDto challengeDto, UserAccountDto userAccountDto) {
        return new ChallengeUserWithChallengeDto(
                1L,
                LocalDateTime.of(2024, 5, 22, 18, 30),
                userAccountDto.id(),
                LocalDateTime.of(2024, 5, 22, 18, 30),
                userAccountDto.id(),
                null,
                challengeDto,
                userAccountDto
        );
    }

    private ChallengeJoinRequestDto createChallengeJoinRequestDto() {
        return new ChallengeJoinRequestDto(1L);
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

    private ChallengeDto createChallengeDto() {
        return new ChallengeDto(
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

}