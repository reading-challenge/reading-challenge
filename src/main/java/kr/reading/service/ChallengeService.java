package kr.reading.service;

import kr.reading.domain.Challenge;
import kr.reading.domain.UserAccount;
import kr.reading.dto.ChallengeDto;
import kr.reading.dto.ChallengeWithImagesDto;
import kr.reading.dto.ChallengeWithImagesWithUsersWithAuthsDto;
import kr.reading.dto.UserAccountDto;
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

    public ChallengeWithImagesDto createChallenge(ChallengeDto dto) {
        Challenge challenge = dto.toEntity();
        return ChallengeWithImagesDto.from(challengeRepository.save(challenge));
    }

    @Transactional(readOnly = true)
    public Page<ChallengeWithImagesDto> getChallenges(Pageable pageable) {
        return challengeRepository.findAllByDeletedAtIsNull(pageable)
                .map(ChallengeWithImagesDto::from);
    }

    @Transactional(readOnly = true)
    public ChallengeWithImagesWithUsersWithAuthsDto getChallenge(Long id) {
        Challenge challenge = findActiveChallengeById(id);
        return ChallengeWithImagesWithUsersWithAuthsDto.from(challenge);
    }

    public ChallengeWithImagesDto updateChallenge(Long challengeId, ChallengeDto dto, UserAccountDto userAccountDto) {
        Challenge challenge = findActiveChallengeById(challengeId);
        UserAccount userAccount = userAccountDto.toEntity();

        if (!challenge.getUserAccount().equals(userAccount)) {
            throw new UserNotMatchException();
        }

        challenge.update(dto.subject(), dto.title(), dto.intro(), dto.description(), dto.recruitedCnt(), dto.startDate(), dto.endDate());

        return ChallengeWithImagesDto.from(challenge);
    }

    public Challenge findActiveChallengeById(Long challengeId) {
        return challengeRepository.findByIdAndDeletedAtIsNull(challengeId)
                .orElseThrow(() -> new ChallengeNotFoundException());
    }

    public void deleteChallenge(Long challengeId, UserAccountDto userAccountDto) throws UserNotMatchException {
        Challenge challenge = findActiveChallengeById(challengeId);
        UserAccount userAccount = userAccountDto.toEntity();

        if (!challenge.getUserAccount().equals(userAccount)) {
            throw new UserNotMatchException();
        }

        challenge.delete();
    }

}
