package kr.reading.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.reading.dto.ChallengeWithImagesDto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record ChallengeWithImagesResponseDto(
        Long id,
        String subject,
        String title,
        String intro,
        String description,
        Integer recruitedCnt,
        Integer hits,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String user,
        @JsonProperty("images") Set<String> challengeImageDtos
) {

    public static ChallengeWithImagesResponseDto from(ChallengeWithImagesDto challengeWithImagesDto) {
        return new ChallengeWithImagesResponseDto(
                challengeWithImagesDto.id(),
                challengeWithImagesDto.subject(),
                challengeWithImagesDto.title(),
                challengeWithImagesDto.intro(),
                challengeWithImagesDto.description(),
                challengeWithImagesDto.recruitedCnt(),
                challengeWithImagesDto.hits(),
                challengeWithImagesDto.startDate(),
                challengeWithImagesDto.endDate(),
                challengeWithImagesDto.userAccountDto().nickname(),
                challengeWithImagesDto.challengeImageDtos().stream()
                        .map(challengeImageDto -> challengeImageDto.imgSrc())
                        .collect(Collectors.toUnmodifiableSet())
        );
    }

}
