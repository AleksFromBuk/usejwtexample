package com.t1.task4.usejwtexample.security;

import com.t1.task4.usejwtexample.entity.RefreshToken;
import com.t1.task4.usejwtexample.entity.User;
import com.t1.task4.usejwtexample.exception.CheckPasswordException;
import com.t1.task4.usejwtexample.exception.RefreshTokenException;
import com.t1.task4.usejwtexample.security.jwt.TokenService;
import com.t1.task4.usejwtexample.service.RefreshTokenService;
import com.t1.task4.usejwtexample.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    public TokenData processPasswordToken(String username, String password) {
        Optional<User> user = userService.findByUsername(username);

        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())) {
            log.error("Exception trying to check password for user {}", username);
            throw new CheckPasswordException();
        }

        return createTokenData(user.get());
    }

    public TokenData processRefreshToken(String refreshTokenValue) {
        Optional<RefreshToken> refreshToken = refreshTokenService.getByValue(refreshTokenValue);
        if (refreshToken.isEmpty()) {
            log.error("Exception trying to check refresh token for user {}", refreshTokenValue);
            throw new RefreshTokenException("Invalid refresh token");
        }
        Optional<User> user = userService.findById(refreshToken.get().getUserId());
        return createTokenData(user.get());
    }

    private TokenData createTokenData(User user) {
        String token = tokenService.generateToken(
                user.getUsername(),
                user.getId().toString(),
                user.getRoles().stream().map(Enum::name).toList()
        );

        RefreshToken refreshToken = refreshTokenService.save(user.getId().toString());
        return new TokenData(token, refreshToken.getValue());
    }

    public record TokenData(String token, String refreshToken) {}
}

