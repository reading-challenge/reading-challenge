package kr.reading.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.reading.dto.request.LoginRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    @Autowired private ObjectMapper objectMapper;
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login", "POST");

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("/login API 해당 메서드를 지원하지 않습니다.: " + request.getMethod());
        } else {
            LoginRequestDto loginRequestDto = objectMapper.readValue(request.getReader(), LoginRequestDto.class);

            if (!StringUtils.hasLength(loginRequestDto.username()) || !StringUtils.hasLength(loginRequestDto.password())) {
                throw new BadCredentialsException("id, pw를 입력하세요.");
            }

            CustomAuthenticationToken token = new CustomAuthenticationToken(
                    loginRequestDto.username(),
                    loginRequestDto.password()
            );
            Authentication authentication = getAuthenticationManager().authenticate(token);
            return authentication;
        }
    }
}
