package kr.reading.dto.request;

import kr.reading.dto.ChallengeDto;

import java.time.LocalDateTime;


public record ChallengeUpdateRequestDto(
        String subject,
        String title,
        String intro,
        String description,
        Integer recruitedCnt,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
    public ChallengeDto toDto() {
        return ChallengeDto.of(
                subject, title, intro, description, recruitedCnt, startDate, endDate
        );
    }
}
