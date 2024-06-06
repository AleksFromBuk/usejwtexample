package com.t1.task4.usejwtexample.security;

import com.t1.task4.usejwtexample.exception.TokenParseException;
import com.t1.task4.usejwtexample.security.jwt.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;


//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class SecurityAuthConverter implements AuthenticationConverter {
//    private final String BEARER_PREFIX = "Bearer ";
//    private final TokenService tokenService;
//
//    @Override
//    public Authentication convert(HttpServletRequest request) {
//        String token = extractBearerToken(request);
//        if (token != null && tokenService.validate(token)) {
//            try {
//                return tokenService.toAuthentication(token);
//            } catch (TokenParseException e) {
//                log.error("Subject, roles and ID must be not null");
//            }
//        }
//        return null;
//    }
//
//    private String extractBearerToken(HttpServletRequest request) {
//        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
//            return authorizationHeader.substring(BEARER_PREFIX.length());
//        }
//        return null;
//    }
//}
@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityAuthConverter {
    private final String BEARER_PREFIX = "Bearer ";
    private final TokenService tokenService;

    public Authentication convert(HttpServletRequest request) {
        String token = extractBearerToken(request);
        if (token != null && tokenService.validate(token)) {
            try {
                return tokenService.toAuthentication(token);
            } catch (TokenParseException e) {
                log.error("Subject, roles and ID must be not null");
            }
        }
        return null;
    }

    private String extractBearerToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            return authorizationHeader.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}