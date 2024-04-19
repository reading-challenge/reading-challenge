package kr.reading.controller;

import kr.reading.global.util.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/logout-success")
    public ResponseEntity<ResponseDTO<String>> root() {
        return ResponseEntity.ok(ResponseDTO.okWithData("로그아웃 성공"));
    }
}
