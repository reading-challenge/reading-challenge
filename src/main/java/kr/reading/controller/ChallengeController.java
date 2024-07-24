package kr.reading.controller;

import kr.reading.dto.request.ChallengeCreationRequestDto;
import kr.reading.dto.request.ChallengeUpdateRequestDto;
import kr.reading.dto.response.ChallengeWithImagesResponseDto;
import kr.reading.dto.response.ChallengeWithImagesWithUsersWithAuthsResponseDto;
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
    public ResponseEntity<ResponseDTO<ChallengeWithImagesResponseDto>> createChallenge(
            @RequestBody ChallengeCreationRequestDto challengeCreationRequestDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        ChallengeWithImagesResponseDto responseDto = ChallengeWithImagesResponseDto.from(challengeService.createChallenge(challengeCreationRequestDto.toDto(principalDetails.toDto())));
        return ResponseEntity.ok(ResponseDTO.okWithData(responseDto));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<Page<ChallengeWithImagesResponseDto>>> getChallenges(
            @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<ChallengeWithImagesResponseDto> responseDtos = challengeService.getChallenges(pageable).map(ChallengeWithImagesResponseDto::from);
        return ResponseEntity.ok(ResponseDTO.okWithData(responseDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ChallengeWithImagesWithUsersWithAuthsResponseDto>> getChallenge(@PathVariable("id") Long id) {
        ChallengeWithImagesWithUsersWithAuthsResponseDto responseDto = ChallengeWithImagesWithUsersWithAuthsResponseDto.from(challengeService.getChallenge(id));
        return ResponseEntity.ok(ResponseDTO.okWithData(responseDto));
    }

    @PatchMapping("{id}")
    public ResponseEntity<ResponseDTO<ChallengeWithImagesResponseDto>> updateChallenge(
            @PathVariable("id") Long id,
            @RequestBody ChallengeUpdateRequestDto updateRequestDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        ChallengeWithImagesResponseDto responseDto = ChallengeWithImagesResponseDto.from(challengeService.updateChallenge(id, updateRequestDto.toDto(), principalDetails.toDto()));
        return ResponseEntity.ok(ResponseDTO.okWithData(responseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteChallenge(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        challengeService.deleteChallenge(id, principalDetails.toDto());
        return ResponseEntity.ok(ResponseDTO.ok());
    }

}

