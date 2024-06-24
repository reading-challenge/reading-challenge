package kr.reading.controller;

import kr.reading.dto.request.ChallengeCreationRequestDto;
import kr.reading.dto.response.ChallengeResponseDto;
import kr.reading.global.util.ResponseDTO;
import kr.reading.security.PrincipalDetails;
import kr.reading.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/challenges")
@RestController
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping
    public ResponseEntity<ResponseDTO<ChallengeResponseDto>> createChallenge(
            @RequestBody ChallengeCreationRequestDto challengeCreationRequestDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        ChallengeResponseDto responseDto = ChallengeResponseDto.from(challengeService.createChallenge(challengeCreationRequestDto.toDto(principalDetails.toDto())));
        return ResponseEntity.ok(ResponseDTO.okWithData(responseDto));
    }

}

