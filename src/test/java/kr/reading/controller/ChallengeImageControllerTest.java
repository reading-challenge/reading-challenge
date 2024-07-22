package kr.reading.controller;

import kr.reading.config.JsonDataEncoder;
import kr.reading.config.TestSecurityConfig;
import kr.reading.global.exception.FileSavedException;
import kr.reading.service.ChallengeImageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ChallengeImageController - 테스트")
@Import({TestSecurityConfig.class, JsonDataEncoder.class})
@WebMvcTest(ChallengeImageController.class)
class ChallengeImageControllerTest {

    @Autowired private MockMvc mvc;
    @Autowired private JsonDataEncoder jsonDataEncoder;

    @MockBean private ChallengeImageService challengeImageService;

    @WithMockUser
    @DisplayName("챌린지 이미지 저장 - 성공")
    @Test
    void givenChallengeIdAndChallengeImages_whenCreating_thenSucceeded() throws Exception {
        // Given
        Long challengeId = 1L;
        MockMultipartFile mockFile1 = new MockMultipartFile("images", "image1.jpg", "image/jpeg", "image1".getBytes());
        given(challengeImageService.createChallengeImage(anyLong(), anyList())).willReturn(Set.of());

        // When & Then
        mvc.perform(multipart("/challenge-images/" + challengeId)
                        .file(mockFile1)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.message").isEmpty());
        then(challengeImageService).should().createChallengeImage(anyLong(), anyList());
    }

    @WithMockUser
    @DisplayName("챌린지 이미지 저장 - 실패")
    @Test
    void givenChallengeIdAndChallengeImages_whenCreating_thenFailed() throws Exception {
        // Given
        Long challengeId = 1L;
        MockMultipartFile mockFile1 = new MockMultipartFile("images", "image1.jpg", "image/jpeg", "image1".getBytes());
        given(challengeImageService.createChallengeImage(anyLong(), anyList())).willThrow(new FileSavedException());

        // When & Then
        mvc.perform(multipart("/challenge-images/" + challengeId)
                        .file(mockFile1)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.message").value(new FileSavedException().getMessage()));
        then(challengeImageService).should().createChallengeImage(anyLong(), anyList());
    }

}