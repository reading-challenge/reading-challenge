package kr.reading.controller;

import kr.reading.dto.ChallengeImageDto;
import kr.reading.dto.response.ChallengeImageResponseDto;
import kr.reading.global.util.ResponseDTO;
import kr.reading.service.ChallengeImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/challenge-images")
@RestController
public class ChallengeImageController {

    private final ChallengeImageService challengeImageService;

    @PostMapping("/{challenge-id}")
    public ResponseEntity<ResponseDTO<Set<ChallengeImageResponseDto>>> createChallengeImage(
            @PathVariable("challenge-id") Long challengeId,
            @RequestPart("images") List<MultipartFile> images
    ) {
        Set<ChallengeImageDto> challengeImageDtos = challengeImageService.createChallengeImage(challengeId, images);
        return ResponseEntity.ok(ResponseDTO.okWithData(
                challengeImageDtos.stream()
                        .map(ChallengeImageResponseDto::from)
                        .collect(Collectors.toUnmodifiableSet())
        ));
    }

}
