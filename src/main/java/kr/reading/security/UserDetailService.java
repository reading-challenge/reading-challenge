package kr.reading.security;

import kr.reading.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUserIdAndDeletedAtIsNull(username)
                .map(PrincipalDetails::from)
                .orElseThrow(() -> new UsernameNotFoundException("계정을 찾을 수 없습니다."));
    }
}
