package com.t1.task4.usejwtexample.repository.Impl;

import com.t1.task4.usejwtexample.entity.RefreshToken;

import com.t1.task4.usejwtexample.exception.RefreshTokenException;
import com.t1.task4.usejwtexample.repository.RefreshTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;


import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Repository
@Slf4j
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
    private static final String REFRESH_TOKEN_INDEX = "refreshTokenIndex";
    private final ValueOperations<String, RefreshToken> valueOps;
    private final HashOperations<String, String, String> hashOps;

    @Autowired
    public RefreshTokenRepositoryImpl(RedisTemplate<String, RefreshToken> redisTemplate) {
        this.valueOps = redisTemplate.opsForValue();
        this.hashOps = redisTemplate.opsForHash();
    }


    @Override
    public Boolean saveRefreshToken(RefreshToken refreshToken, Duration expTime) {
        String refreshTokenId = refreshToken.getId();
        Boolean valueSet = valueOps.setIfAbsent(refreshTokenId, refreshToken, expTime.toMillis(), TimeUnit.MILLISECONDS);
        Boolean hashSet = hashOps.putIfAbsent(REFRESH_TOKEN_INDEX, refreshToken.getValue(), refreshTokenId);
        return Boolean.TRUE.equals(valueSet) && hashSet;
    }

    @Override
    public RefreshToken getByValue(String refreshToken) {
        String refreshTokenId = hashOps.get(REFRESH_TOKEN_INDEX, refreshToken);
        if (refreshTokenId == null) {
            throw new RefreshTokenException("Refresh token not found: " + refreshToken);
        }

        Long cleanupCount = hashOps.delete(REFRESH_TOKEN_INDEX, refreshToken);
        RefreshToken token = valueOps.get(refreshTokenId);
        if (token == null) {
            throw new RefreshTokenException("Refresh token not found: " + refreshToken);
        }
        log.info("Cleanup refreshToken hash count: {}", cleanupCount);
        return token;
    }
}
