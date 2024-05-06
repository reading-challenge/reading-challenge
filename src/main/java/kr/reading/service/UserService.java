package kr.reading.service;

import kr.reading.domain.User;
import kr.reading.dto.UserDto;
import kr.reading.global.exception.EmailExistsException;
import kr.reading.global.exception.NicNameExistsException;
import kr.reading.global.exception.UserIdExistsException;
import kr.reading.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto signup(UserDto dto) {
        userRepository.findByUserId(dto.userId()).ifPresent(user -> {
            throw new UserIdExistsException();
        });

        userRepository.findByEmail(dto.email()).ifPresent(user -> {
            throw new EmailExistsException();
        });

        userRepository.findByNickname(dto.nickname()).ifPresent(user -> {
            throw new NicNameExistsException();
        });

        User entity = dto.toEntity();
        entity.encodedPassword(passwordEncoder.encode(dto.userPw()));
        User registerdUser = userRepository.save(entity);

        return UserDto.from(registerdUser);
    }
}
