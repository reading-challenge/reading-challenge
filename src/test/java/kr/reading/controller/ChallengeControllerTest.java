package kr.reading.controller;


import kr.reading.config.JsonDataEncoder;
import kr.reading.config.TestSecurityConfig;
import kr.reading.dto.ChallengeDto;
import kr.reading.dto.UserDto;
import kr.reading.dto.request.ChallengeCreationRequestDto;
import kr.reading.global.exception.ChallengeNotFoundException;
import kr.reading.global.exception.InactiveUserException;
import kr.reading.service.ChallengeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ChallengeController - 테스트")
@Import({TestSecurityConfig.class, JsonDataEncoder.class})
@WebMvcTest(ChallengeController.class)
class ChallengeControllerTest {

    private static final Object CHALLENGE_NOT_FOUND_MSG = "챌린지가 존재하지 않습니다.";

    @Autowired private MockMvc mvc;
    @Autowired private JsonDataEncoder jsonDataEncoder;

    @MockBean private ChallengeService challengeService;

    @WithUserDetails(value = "user1", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("챌린지 생성 - 성공")
    @Test
    void giveChallengeInfo_whenCreatingChallenge_thenSucceeded() throws Exception {
        // Given
        ChallengeCreationRequestDto challengeCreationRequestDto = createChallengeRequestDto();
        ChallengeDto challengeDto = createChallengeDto();
        given(challengeService.createChallenge(any(ChallengeDto.class))).willReturn(challengeDto);

        // When & Then
        mvc.perform(post("/challenges")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonDataEncoder.encode(challengeCreationRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.message").isEmpty());
        then(challengeService).should().createChallenge(any(ChallengeDto.class));
    }

    @WithMockUser
    @DisplayName("챌린지 조회 - 성공")
    @Test
    void givenNothing_whenGettingChallenges_thenSucceeded() throws Exception {
        // Given
        given(challengeService.getChallenges(any(Pageable.class))).willReturn(Page.empty());

        // When & Then
        mvc.perform(get("/challenges")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.message").isEmpty());
        then(challengeService).should().getChallenges(any(Pageable.class));
    }

    @WithMockUser
    @DisplayName("챌린지 상세 조회 - 성공")
    @Test
    void givenChallengeId_whenGettingChallenge_thenSucceeded() throws Exception {
        // Given
        ChallengeDto challengeDto = createChallengeDto();
        given(challengeService.getChallenge(anyLong())).willReturn(challengeDto);

        // When & then
        mvc.perform(get("/challenges/"+challengeDto.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.message").isEmpty());

        then(challengeService).should().getChallenge(anyLong());
    }

    @WithMockUser
    @DisplayName("챌린지 상세 조회 - 실패")
    @Test
    void givenChallengeId_whenGettingChallenge_thenFailed() throws Exception {
        // Given
        ChallengeDto challengeDto = createChallengeDto();
        given(challengeService.getChallenge(anyLong())).willThrow(new ChallengeNotFoundException());

        // When & then
        mvc.perform(get("/challenges/"+challengeDto.id()))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.message").value(CHALLENGE_NOT_FOUND_MSG));

        then(challengeService).should().getChallenge(anyLong());
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

    private ChallengeCreationRequestDto createChallengeRequestDto() {
        return ChallengeCreationRequestDto.of(
                "자기계발",
                "자기계발 독서 챌린지 제목",
                "자기계발 독서 챌린지입니다.",
                "챌린지에 참여해 자기계발을 해보아요.",
                10,
                LocalDateTime.of(2024, 5, 22, 18, 30),
                LocalDateTime.of(2024, 10, 22, 18, 30)
        );
    }

}