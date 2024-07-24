package kr.reading.controller;


import kr.reading.config.JsonDataEncoder;
import kr.reading.config.TestSecurityConfig;
import kr.reading.dto.ChallengeDto;
import kr.reading.dto.ChallengeWithImagesDto;
import kr.reading.dto.ChallengeWithImagesWithUsersWithAuthsDto;
import kr.reading.dto.UserAccountDto;
import kr.reading.dto.request.ChallengeCreationRequestDto;
import kr.reading.dto.request.ChallengeUpdateRequestDto;
import kr.reading.global.exception.ChallengeNotFoundException;
import kr.reading.global.exception.UserNotMatchException;
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
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ChallengeController - 테스트")
@Import({TestSecurityConfig.class, JsonDataEncoder.class})
@WebMvcTest(ChallengeController.class)
class ChallengeControllerTest {

    private static final Object CHALLENGE_NOT_FOUND_MSG = "챌린지가 존재하지 않습니다.";
    private static final Object USER_NOT_MISMATCH_MSG = "유저가 일치하지 않습니다.";

    @Autowired private MockMvc mvc;
    @Autowired private JsonDataEncoder jsonDataEncoder;

    @MockBean private ChallengeService challengeService;

    @WithUserDetails(value = "user1", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("챌린지 생성 - 성공")
    @Test
    void giveChallengeInfo_whenCreatingChallenge_thenSucceeded() throws Exception {
        // Given
        ChallengeCreationRequestDto challengeCreationRequestDto = createChallengeRequestDto();
        ChallengeWithImagesDto challengeWithImagesDto = createChallengeWithImagesDto();
        given(challengeService.createChallenge(any(ChallengeDto.class))).willReturn(challengeWithImagesDto);

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
        ChallengeWithImagesWithUsersWithAuthsDto challengeWithImagesWithUsersWithAuthsDto = createChallengeWithImagesWithUsersWithAuthsDto();
        given(challengeService.getChallenge(anyLong())).willReturn(challengeWithImagesWithUsersWithAuthsDto);

        // When & then
        mvc.perform(get("/challenges/" + challengeWithImagesWithUsersWithAuthsDto.id()))
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
        ChallengeWithImagesWithUsersWithAuthsDto challengeDto = createChallengeWithImagesWithUsersWithAuthsDto();
        given(challengeService.getChallenge(anyLong())).willThrow(new ChallengeNotFoundException());

        // When & then
        mvc.perform(get("/challenges/" + challengeDto.id()))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.message").value(CHALLENGE_NOT_FOUND_MSG));

        then(challengeService).should().getChallenge(anyLong());
    }

    @WithUserDetails(value = "user1", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("챌린지 업데이트 - 성공")
    @Test
    void giveChallengeUpdateInfo_whenUpdatingChallenge_thenSucceeded() throws Exception {
        // Given
        Long challengeId = 1L;
        ChallengeUpdateRequestDto challengeUpdateRequestDto = createChallengeUpdateRequestDto();
        ChallengeWithImagesDto challengeWithImagesDto = createChallengeWithImagesDto();
        given(challengeService.updateChallenge(anyLong(), any(ChallengeDto.class), any(UserAccountDto.class)))
                .willReturn(challengeWithImagesDto);

        // When & Then
        mvc.perform(patch("/challenges/" + challengeId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonDataEncoder.encode(challengeUpdateRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.message").isEmpty());
        then(challengeService).should().updateChallenge(anyLong(), any(ChallengeDto.class), any(UserAccountDto.class));
    }

    @WithUserDetails(value = "user1", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("챌린지 업데이트 - 실패")
    @Test
    void giveChallengeUpdateInfo_whenUpdatingChallenge_thenFailed() throws Exception {
        // Given
        Long challengeId = 1L;
        ChallengeUpdateRequestDto challengeUpdateRequestDto = createChallengeUpdateRequestDto();
        given(challengeService.updateChallenge(anyLong(), any(ChallengeDto.class), any(UserAccountDto.class)))
                .willThrow(new UserNotMatchException());

        // When & Then
        mvc.perform(patch("/challenges/" + challengeId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonDataEncoder.encode(challengeUpdateRequestDto)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.message").value(USER_NOT_MISMATCH_MSG));
        then(challengeService).should().updateChallenge(anyLong(), any(ChallengeDto.class), any(UserAccountDto.class));
    }

    @WithUserDetails(value = "user1", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("챌린지 삭제 - 성공")
    @Test
    void giveChallengeId_whenDeletingChallenge_thenSucceeded() throws Exception {
        // Given
        Long challengeId = 1L;
        willDoNothing().given(challengeService).deleteChallenge(anyLong(), any(UserAccountDto.class));

        // When & Then
        mvc.perform(delete("/challenges/" + challengeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.message").isEmpty());
        then(challengeService).should().deleteChallenge(anyLong(), any(UserAccountDto.class));
    }

    @WithUserDetails(value = "user1", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("챌린지 삭제 - 실패")
    @Test
    void giveChallengeId_whenDeletingChallenge_thenFailed() throws Exception {
        // Given
        Long challengeId = 1L;
        willThrow(new UserNotMatchException()).given(challengeService).deleteChallenge(anyLong(), any(UserAccountDto.class));

        // When & Then
        mvc.perform(delete("/challenges/" + challengeId))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.message").value(USER_NOT_MISMATCH_MSG));
        then(challengeService).should().deleteChallenge(anyLong(), any(UserAccountDto.class));
    }

    private ChallengeUpdateRequestDto createChallengeUpdateRequestDto() {
        return new ChallengeUpdateRequestDto(
                "자기계발",
                "자기계발 독서 챌린지 제목",
                "자기계발 독서 챌린지입니다.",
                "챌린지에 참여해 자기계발을 해보아요.",
                10,
                LocalDateTime.of(2024, 5, 22, 18, 30),
                LocalDateTime.of(2024, 10, 22, 18, 30)
        );
    }

    private ChallengeWithImagesWithUsersWithAuthsDto createChallengeWithImagesWithUsersWithAuthsDto() {
        return new ChallengeWithImagesWithUsersWithAuthsDto(
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
                createUserDto(),
                Set.of(),
                Set.of(),
                Set.of()
        );
    }

    private ChallengeWithImagesDto createChallengeWithImagesDto() {
        return new ChallengeWithImagesDto(
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
                createUserDto(),
                Set.of()
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