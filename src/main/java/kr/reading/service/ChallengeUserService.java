package kr.reading.service;

import kr.reading.domain.Challenge;
import kr.reading.domain.ChallengeUser;
import kr.reading.domain.UserAccount;
import kr.reading.dto.ChallengeUserDto;
import kr.reading.dto.request.ChallengeUserWithChallengeDto;
import kr.reading.global.exception.AlreadyChallengeJoinException;
import kr.reading.repository.ChallengeUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class ChallengeUserService {

    private final ChallengeUserRepository challengeUserRepository;
    private final ChallengeService challengeService;
    private final UserService userService;

    public ChallengeUserWithChallengeDto joinChallenge(ChallengeUserDto dto) {
        Challenge challenge = challengeService.findActiveChallengeById(dto.challengeId());
        UserAccount userAccount = userService.findActiveUserById(dto.userAccountDto().id());

        Optional<ChallengeUser> findChallengeUser = challengeUserRepository.findByChallengeAndUserAccount(challenge, userAccount);

        if (findChallengeUser.isPresent()) {
            throw new AlreadyChallengeJoinException();
        }

        ChallengeUser challengeUser = dto.toEntity(challenge, userAccount);
        return ChallengeUserWithChallengeDto.from(challengeUserRepository.save(challengeUser));
    }

}
