package kr.reading.controller;

import kr.reading.global.util.ResponseDTO;
import kr.reading.service.ChallengeImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/challenge-images")
@RestController
public class ChallengeImageController {

    private final ChallengeImageService challengeImageService;

    @PostMapping("/{challenge-id}")
    public ResponseEntity<ResponseDTO<Void>> createChallengeImage(
            @PathVariable("challenge-id") Long challengeId,
            @RequestPart("images") List<MultipartFile> images
    ) {
        challengeImageService.createChallengeImage(challengeId, images);
        return ResponseEntity.ok(ResponseDTO.ok());
    }

}
