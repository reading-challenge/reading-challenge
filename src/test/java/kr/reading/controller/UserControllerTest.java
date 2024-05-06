package kr.reading.controller;

import kr.reading.config.JsonDataEncoder;
import kr.reading.config.TestSecurityConfig;
import kr.reading.dto.UserDto;
import kr.reading.dto.request.SignupRequestDto;
import kr.reading.global.exception.InactiveUserException;
import kr.reading.global.exception.UserIdExistsException;
import kr.reading.service.UserService;
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


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("UserController - 테스트")
@Import({TestSecurityConfig.class, JsonDataEncoder.class})
@WebMvcTest(UserController.class)
class UserControllerTest {
    private static final String USERID_EXISTS_EXCEPTION_MESSAGE = "이미 등록된 유저 ID 입니다.";
    private static final String INACTIVE_USER_EXCEPTION_MESSAGE = "이미 탈퇴 되었거나 가입된 유저가 아닙니다.";

    @Autowired private MockMvc mvc;
    @Autowired private JsonDataEncoder jsonDataEncoder;

    @MockBean private UserService userService;

    @DisplayName("로그아웃 성공 API")
    @Test
    void givenNothing_whenLogoutSuccess_thenSuccess() throws Exception {
        // When & then
        mvc.perform(get("/logout-success"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").value("로그아웃 성공"))
                .andExpect(jsonPath("$.message").isEmpty());
    }

    @DisplayName("회원가입 - 성공")
    @Test
    void givenSignupInfo_whenSignup_thenSucceeded() throws Exception {
        // Given
        UserDto userDto = createUserDto();
        SignupRequestDto signupRequestDto = createSignupRequestDto();
        given(userService.signup(any(UserDto.class))).willReturn(userDto);

        // When & then
        mvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonDataEncoder.encode(signupRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.message").isEmpty());

        then(userService).should().signup(any(UserDto.class));
    }

    @DisplayName("회원가입 - 실패")
    @Test
    void givenSignupInfo_whenSignup_thenFailed() throws Exception {
        // Given
        SignupRequestDto signupRequestDto = createSignupRequestDto();
        given(userService.signup(any(UserDto.class))).willThrow(new UserIdExistsException());

        // When & then
        mvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonDataEncoder.encode(signupRequestDto)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.message").value(USERID_EXISTS_EXCEPTION_MESSAGE));

        then(userService).should().signup(any(UserDto.class));
    }

    @WithUserDetails(value = "user1", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("회원탈퇴 - 성공")
    @Test
    void givenUser_whenDeleteUser_thenSucceeded() throws Exception {
        // Given
        UserDto userDto = createUserDto();
        given(userService.deleteUser(anyLong())).willReturn(userDto);

        // When & then
        mvc.perform(delete("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.message").isEmpty());

        then(userService).should().deleteUser(anyLong());
    }

    @WithUserDetails(value = "user1", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("회원탈퇴 - 실패")
    @Test
    void givenInactiveUser_whenDeleteUser_thenFailed() throws Exception {
        // Given
        UserDto userDto = createUserDto();
        given(userService.deleteUser(anyLong())).willThrow(new InactiveUserException());

        // When & then
        mvc.perform(delete("/users"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.message").value(INACTIVE_USER_EXCEPTION_MESSAGE));

        then(userService).should().deleteUser(anyLong());
    }

    private UserDto createUserDto() {
        UserDto userDto = UserDto.of(
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

        return userDto;
    }

    private SignupRequestDto createSignupRequestDto() {
        return new SignupRequestDto(
                "user1",
                "password1",
                "user1@email.com",
                "010-1234-1234",
                LocalDate.of(2000, 4, 22),
                "자기계발",
                "닉네임"
        );
    }
}