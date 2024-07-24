package kr.reading.dto.request;

import kr.reading.dto.ChallengeDto;
import kr.reading.dto.UserAccountDto;

import java.time.LocalDateTime;


public record ChallengeCreationRequestDto(
        String subject,
        String title,
        String intro,
        String description,
        Integer recruitedCnt,
        LocalDateTime startDate,
        LocalDateTime endDate
) {

    public static ChallengeCreationRequestDto of(String subject,
                                                 String title,
                                                 String intro,
                                                 String description,
                                                 Integer recruitedCnt,
                                                 LocalDateTime startDate,
                                                 LocalDateTime endDate
    ) {
        return new ChallengeCreationRequestDto(subject, title, intro, description, recruitedCnt, startDate, endDate);
    }

    public ChallengeDto toDto(UserAccountDto userAccountDto) {
        return ChallengeDto.of(
                subject,
                title,
                intro,
                description,
                recruitedCnt,
                startDate,
                endDate,
                userAccountDto
        );
    }

}
