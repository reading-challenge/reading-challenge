package kr.reading.controller;

import kr.reading.dto.request.SignupRequestDto;
import kr.reading.dto.response.UserResponseDto;
import kr.reading.global.util.ResponseDTO;
import kr.reading.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/logout-success")
    public ResponseEntity<ResponseDTO<String>> logoutSuccess() {
        return ResponseEntity.ok(ResponseDTO.okWithData("로그아웃 성공"));
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO<UserResponseDto>> signup(@RequestBody SignupRequestDto signupRequestDto) {
        UserResponseDto userResponseDto = UserResponseDto.from(userService.signup(signupRequestDto.toDto()));
        return ResponseEntity.ok(ResponseDTO.okWithData(userResponseDto));
    }
}
