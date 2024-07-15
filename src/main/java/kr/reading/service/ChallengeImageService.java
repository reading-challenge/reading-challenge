package kr.reading.service;

import kr.reading.domain.Challenge;
import kr.reading.domain.ChallengeImage;
import kr.reading.dto.ChallengeImageDto;
import kr.reading.repository.ChallengeImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Transactional
@RequiredArgsConstructor
@Service
public class ChallengeImageService {

    private final FileService fileService;
    private final ChallengeImageRepository challengeImageRepository;
    private final ChallengeService challengeService;

    public Set<ChallengeImageDto> createChallengeImage(Long challengeId, List<MultipartFile> images) {
        Set<ChallengeImageDto> challengeImageDtos = new LinkedHashSet<>();

        Challenge challenge = challengeService.findActiveChallengeById(challengeId);

        for (MultipartFile image : images) {
            String imgUrl = fileService.saveImage(image);

            ChallengeImage challengeImage = ChallengeImage.of(imgUrl, challenge);
            ChallengeImage savedChallenge = challengeImageRepository.save(challengeImage);

            challengeImageDtos.add(ChallengeImageDto.from(savedChallenge));
        }

        return challengeImageDtos;
    }

}
