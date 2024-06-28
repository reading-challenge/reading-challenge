package kr.reading.service;

import kr.reading.domain.Challenge;
import kr.reading.dto.ChallengeDto;
import kr.reading.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    public ChallengeDto createChallenge(ChallengeDto dto) {
        Challenge challenge = dto.toEntity();
        return ChallengeDto.from(challengeRepository.save(challenge));
    }

    @Transactional(readOnly = true)
    public Page<ChallengeDto> getChallenges(Pageable pageable) {
        return challengeRepository.findAllByDeletedAtIsNull(pageable).map(ChallengeDto::from);
    }

}
