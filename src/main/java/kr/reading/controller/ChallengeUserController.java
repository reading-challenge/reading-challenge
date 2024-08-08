package kr.reading.controller;

import kr.reading.dto.request.ChallengeJoinRequestDto;
import kr.reading.dto.response.ChallengeUserWithChallengeResponseDto;
import kr.reading.global.util.ResponseDTO;
import kr.reading.security.PrincipalDetails;
import kr.reading.service.ChallengeUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/challenge-users")
@RestController
public class ChallengeUserController {

    private final ChallengeUserService challengeUserService;

    @PostMapping
    public ResponseEntity<ResponseDTO<ChallengeUserWithChallengeResponseDto>> joinChallenge(
            @RequestBody ChallengeJoinRequestDto challengeJoinRequestDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        ChallengeUserWithChallengeResponseDto responseDto = ChallengeUserWithChallengeResponseDto.from(
                challengeUserService.joinChallenge(challengeJoinRequestDto.toDto(principalDetails.toDto()))
        );
        return ResponseEntity.ok(ResponseDTO.okWithData(responseDto));
    }

}
