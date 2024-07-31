package kr.reading.service;

import kr.reading.domain.UserAccount;
import kr.reading.dto.UserAccountDto;
import kr.reading.global.exception.*;
import kr.reading.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserAccount findActiveUserById(Long id) {
        return userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(InactiveUserException::new);
    }

    public UserAccountDto signup(UserAccountDto dto) {
        userRepository.findByUserId(dto.userId()).ifPresent(user -> {
            throw new UserIdExistsException();
        });

        userRepository.findByEmail(dto.email()).ifPresent(user -> {
            throw new EmailExistsException();
        });

        userRepository.findByNickname(dto.nickname()).ifPresent(user -> {
            throw new NicNameExistsException();
        });

        UserAccount entity = dto.toEntity();
        entity.encodedPassword(passwordEncoder.encode(dto.userPw()));
        UserAccount registeredUser = userRepository.save(entity);

        return UserAccountDto.from(registeredUser);
    }

    public void deleteUser(Long id) {
        UserAccount userAccount = findActiveUserById(id);
        userAccount.delete();
    }

}
