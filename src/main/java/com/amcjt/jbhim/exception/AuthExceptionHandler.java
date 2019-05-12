package com.amcjt.jbhim.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于处理各种认证异常，比如用户名错误 密码错误 验证码错误 返回一个json
 */
@Component
public class AuthExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler, WebResponseExceptionTranslator,
        AuthenticationFailureHandler {
    private ObjectMapper objectMapper;

    public AuthExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws ServletException, IOException {
        String message = "坏的凭证".equals(authException.getMessage()) ? "密码错误" : authException.getMessage();
        Map<String, Object> map = new HashMap<>();
        map.put("error", "400");
        setData(request, response, map, message);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), map);
        //logService.writeLog(request, message);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> map = new HashMap<>();
        map.put("error", "403");
        setData(request, response, map, accessDeniedException.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(map));
        //logService.writeLog(request, accessDeniedException.getMessage());
    }

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) {
        return ResponseEntity
                .status(400)
                .body(new CustomOAuthException(e.getMessage()));
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException {
        Map<String, Object> map = new HashMap<>();
        setData(request, response, map, exception.getMessage());
        response.setStatus(400);
        response.getWriter().write(objectMapper.writeValueAsString(map));
        //logService.writeLog(request, exception.getMessage());
    }

    private void setData(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map, String message) {
        map.put("message", message);
        map.put("path", request.getServletPath());
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

}
