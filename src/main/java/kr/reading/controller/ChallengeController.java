package kr.reading.controller;

import kr.reading.dto.request.ChallengeCreationRequestDto;
import kr.reading.dto.response.ChallengeResponseDto;
import kr.reading.global.util.ResponseDTO;
import kr.reading.security.PrincipalDetails;
import kr.reading.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<ResponseDTO<Page<ChallengeResponseDto>>> getChallenges(
            @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<ChallengeResponseDto> challenges = challengeService.getChallenges(pageable).map(ChallengeResponseDto::from);
        return ResponseEntity.ok(ResponseDTO.okWithData(challenges));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ChallengeResponseDto>> getChallenge(@PathVariable("id") Long id) {
        ChallengeResponseDto challenge = ChallengeResponseDto.from(challengeService.getChallenge(id));
        return ResponseEntity.ok(ResponseDTO.okWithData(challenge));
    }

}

