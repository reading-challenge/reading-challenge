package kr.reading.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.reading.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;


@RequiredArgsConstructor
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        ResponseDTO<Void> responseDTO = ResponseDTO.errorWithMessage(HttpStatus.FORBIDDEN, "해당 리소스에 접근 권한이 없습니다.");
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