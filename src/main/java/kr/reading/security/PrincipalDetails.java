package kr.reading.security;

import kr.reading.domain.UserAccount;
import kr.reading.dto.UserDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record PrincipalDetails(
        Long id,
        String userId,
        String userPw,
        String email,
        String phone,
        LocalDate birthday,
        String favoriteSub,
        String profileSrc,
        String nickname,
        Collection<? extends GrantedAuthority> authorities
) implements UserDetails {

    public static PrincipalDetails of(Long id, String userId, String userPw,
                                      String email, String phone, LocalDate birthday,
                                      String favoriteSub, String profileSrc, String nickname) {
        Set<RoleType> roleTypes = Set.of(RoleType.USER);

        return new PrincipalDetails(
                id, userId, userPw, email, phone,
                birthday, favoriteSub, profileSrc, nickname,
                roleTypes.stream()
                        .map(RoleType::getName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }

    public static PrincipalDetails from(UserAccount entity) {
        return PrincipalDetails.of(
                entity.getId(),
                entity.getUserId(),
                entity.getUserPw(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getBirthday(),
                entity.getFavoriteSub(),
                entity.getProfileSrc(),
                entity.getNickname()
        );
    }

    public UserDto toDto() {
        return UserDto.of(id, userId, userPw, email, phone, birthday, favoriteSub, profileSrc, nickname);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userPw;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public enum RoleType {
        USER("ROLE_USER");

        @Getter
        private final String name;

        RoleType(String name) {
            this.name = name;
        }
    }
}
