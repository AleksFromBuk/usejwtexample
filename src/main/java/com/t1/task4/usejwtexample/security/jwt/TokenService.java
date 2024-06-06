package com.t1.task4.usejwtexample.security.jwt;

import com.t1.task4.usejwtexample.exception.TokenParseException;
import com.t1.task4.usejwtexample.security.AppUserPrincipal;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class TokenService {

    private static final String ROLE_CLAIM = "role";
    private static final String ID_CLAIM = "id";

    @Value("${user-service.jwt.secret}")
    private String jwtSecret;

    @Value("${user-service.jwt.tokenExpiration}")
    private Duration tokenExpiration;

    @PostConstruct
    protected void init() {
        jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
    }


    public String generateToken(String username, String id, List<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + tokenExpiration.toMillis()))
                .claim(ROLE_CLAIM, roles)
                .claim(ID_CLAIM, id)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    @SuppressWarnings("unchecked")
    public Authentication toAuthentication(String token) throws TokenParseException {
        Claims tokenBody = getBody(token);
        String subject = tokenBody.getSubject();
        String id = tokenBody.get(ID_CLAIM, String.class);
        List<String> roles = (List<String>) tokenBody.get(ROLE_CLAIM);

        if (Objects.isNull(subject) || Objects.isNull(roles) || Objects.isNull(id)) {
            log.error("Subject, role or id is null. Subject: {}; Roles: {}; ID: {}", subject, roles, id);
            throw new TokenParseException("Subject, roles and ID must be not null");
        }

        if (subject.isBlank() || roles.isEmpty()) {
            log.error("Subject, role or ID is empty. Subject: {}; Roles: {}; ID: {}", subject, roles, id);
            throw new TokenParseException("Subject, roles and ID must be not empty");
        }

        AppUserPrincipal principal = new AppUserPrincipal(subject, id, roles);
        return new UsernamePasswordAuthenticationToken(
                principal,
                null,
                roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }

    public boolean validate(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    private Claims getBody(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }
}