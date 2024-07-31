package kr.reading.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.reading.dto.request.SignupRequestDto;
import kr.reading.dto.response.UserAccountResponseDto;
import kr.reading.global.util.ResponseDTO;
import kr.reading.security.PrincipalDetails;
import kr.reading.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {
    private static final String SESSION_COOKIE_NAME = "JSESSIONID";

    private final UserService userService;

    @GetMapping("/logout-success")
    public ResponseEntity<ResponseDTO<String>> logoutSuccess() {
        return ResponseEntity.ok(ResponseDTO.okWithData("로그아웃 성공"));
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO<UserAccountResponseDto>> signup(@RequestBody SignupRequestDto signupRequestDto) {
        UserAccountResponseDto responseDto = UserAccountResponseDto.from(userService.signup(signupRequestDto.toDto()));
        return ResponseEntity.ok(ResponseDTO.okWithData(responseDto));
    }

    @DeleteMapping("/users")
    public ResponseEntity<ResponseDTO<Void>> deleteUser(
            HttpServletRequest request,
            HttpServletResponse response,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        userService.deleteUser(principalDetails.id());

        deleteCookie(response);
        deleteSession(request);

        return ResponseEntity.ok(ResponseDTO.ok());
    }

    private void deleteCookie(HttpServletResponse response) {
        Cookie sessionCookie = new Cookie(SESSION_COOKIE_NAME, null);
        sessionCookie.setMaxAge(0);
        response.addCookie(sessionCookie);
    }

    private void deleteSession(HttpServletRequest request) {
        request.getSession().invalidate();
    }
}
