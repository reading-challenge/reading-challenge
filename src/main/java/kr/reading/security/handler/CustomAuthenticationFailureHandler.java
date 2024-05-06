package kr.reading.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.reading.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;


@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        String errMsg = exception.getMessage();
        ResponseDTO<Void> responseDTO = ResponseDTO.errorWithMessage(HttpStatus.BAD_REQUEST, errMsg);
        sendResponse(response, responseDTO);
    }

    private void sendResponse(HttpServletResponse response, ResponseDTO<Void> responseDTO) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(responseDTO.getCode());
        PrintWriter out = response.getWriter();
        out.print(objectMapper.writeValueAsString(responseDTO));
        out.flush();
    }
}
