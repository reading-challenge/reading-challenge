package kr.reading.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.reading.dto.ChallengeAuthDto;
import kr.reading.dto.ChallengeUserDto;
import kr.reading.dto.ChallengeWithImagesWithUsersWithAuthsDto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record ChallengeWithImagesWithUsersWithAuthsResponseDto(
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
        @JsonProperty("images") Set<String> challengeImageDtos,
        @JsonProperty("challengeUsers") Set<ChallengeUserResponseDto> challengeUserResponseDtos,
        @JsonProperty("auths") Set<ChallengeAuthResponseDto> challengeAuthResponseDtos
) {

    public record ChallengeUserResponseDto(
            Long id,
            String user
    ) {

        public static ChallengeUserResponseDto from(ChallengeUserDto challengeUserDto) {
            return new ChallengeUserResponseDto(
                    challengeUserDto.id(),
                    challengeUserDto.userAccountDto().nickname()
            );
        }
    }

    public record ChallengeAuthResponseDto(
            Long id,
            String title,
            String content,
            LocalDateTime createdAt,
            String user
    ) {

        public static ChallengeAuthResponseDto from(ChallengeAuthDto challengeAuthDto) {
            return new ChallengeAuthResponseDto(
                    challengeAuthDto.id(),
                    challengeAuthDto.title(),
                    challengeAuthDto.content(),
                    challengeAuthDto.createdAt(),
                    challengeAuthDto.userAccountDto().nickname()
            );
        }
    }

    public static ChallengeWithImagesWithUsersWithAuthsResponseDto from(ChallengeWithImagesWithUsersWithAuthsDto challengeWithImagesWithUsersWithAuthsDto) {
        return new ChallengeWithImagesWithUsersWithAuthsResponseDto(
                challengeWithImagesWithUsersWithAuthsDto.id(),
                challengeWithImagesWithUsersWithAuthsDto.subject(),
                challengeWithImagesWithUsersWithAuthsDto.title(),
                challengeWithImagesWithUsersWithAuthsDto.intro(),
                challengeWithImagesWithUsersWithAuthsDto.description(),
                challengeWithImagesWithUsersWithAuthsDto.recruitedCnt(),
                challengeWithImagesWithUsersWithAuthsDto.hits(),
                challengeWithImagesWithUsersWithAuthsDto.startDate(),
                challengeWithImagesWithUsersWithAuthsDto.endDate(),
                challengeWithImagesWithUsersWithAuthsDto.userAccountDto().nickname(),
                challengeWithImagesWithUsersWithAuthsDto.challengeImageDtos().stream()
                        .map(challengeImageDto -> challengeImageDto.imgSrc())
                        .collect(Collectors.toUnmodifiableSet()),
                challengeWithImagesWithUsersWithAuthsDto.challengeUserDtos().stream()
                        .map(ChallengeUserResponseDto::from)
                        .collect(Collectors.toUnmodifiableSet()),
                challengeWithImagesWithUsersWithAuthsDto.challengeAuthDtos().stream()
                        .map(ChallengeAuthResponseDto::from)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }

}
