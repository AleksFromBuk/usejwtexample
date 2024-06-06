package com.t1.task4.usejwtexample.service.impl;

import com.t1.task4.usejwtexample.entity.RefreshToken;
import com.t1.task4.usejwtexample.exception.RefreshTokenException;
import com.t1.task4.usejwtexample.repository.RefreshTokenRepository;
import com.t1.task4.usejwtexample.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository repository;

    @Value("${user-service.jwt.refreshTokenExpiration}")
    private Duration refreshTokenExpiration;

    @Override
    public RefreshToken save(String userId) {
        String refreshTokenValue = UUID.randomUUID().toString();
        String id = UUID.randomUUID().toString();
        var refreshToken = new RefreshToken(id, userId, refreshTokenValue);
        repository.saveRefreshToken(refreshToken, refreshTokenExpiration);
        return refreshToken;

    }

    @Override
    public Optional<RefreshToken> getByValue(String refreshToken) {
        try {
            return Optional.ofNullable(repository.getByValue(refreshToken));
        } catch (RefreshTokenException e) {
            return Optional.empty();
        }
    }
}
