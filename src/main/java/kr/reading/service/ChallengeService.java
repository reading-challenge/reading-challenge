package kr.reading.service;

import kr.reading.domain.Challenge;
import kr.reading.domain.UserAccount;
import kr.reading.dto.ChallengeDto;
import kr.reading.dto.UserDto;
import kr.reading.global.exception.ChallengeNotFoundException;
import kr.reading.global.exception.UserNotMatchException;
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
        return challengeRepository.findAllByDeletedAtIsNull(pageable)
                .map(ChallengeDto::from);
    }

    @Transactional(readOnly = true)
    public ChallengeDto getChallenge(Long id) {
        Challenge challenge = findActiveChallengeById(id);
        return ChallengeDto.from(challenge);
    }

    public ChallengeDto updateChallenge(Long challengeId, ChallengeDto dto, UserDto userDto) {
        Challenge challenge = findActiveChallengeById(challengeId);
        UserAccount userAccount = userDto.toEntity();

        if (!challenge.getUserAccount().equals(userAccount)) {
            throw new UserNotMatchException();
        }

        challenge.update(dto.subject(), dto.title(), dto.intro(), dto.description(), dto.recruitedCnt(), dto.startDate(), dto.endDate());

        return ChallengeDto.from(challenge);
    }

    private Challenge findActiveChallengeById(Long challengeId) {
        return challengeRepository.findByIdAndDeletedAtIsNull(challengeId)
                .orElseThrow(() -> new ChallengeNotFoundException());
    }

    public void deleteChallenge(Long challengeId, UserDto userDto) throws UserNotMatchException {
        Challenge challenge = findActiveChallengeById(challengeId);
        UserAccount userAccount = userDto.toEntity();

        if (!challenge.getUserAccount().equals(userAccount)) {
            throw new UserNotMatchException();
        }

        challenge.delete();
    }

}
