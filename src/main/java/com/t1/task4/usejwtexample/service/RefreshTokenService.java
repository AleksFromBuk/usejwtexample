package com.t1.task4.usejwtexample.service;

import com.t1.task4.usejwtexample.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken save(String userId);
    Optional<RefreshToken> getByValue(String refreshToken);
}

