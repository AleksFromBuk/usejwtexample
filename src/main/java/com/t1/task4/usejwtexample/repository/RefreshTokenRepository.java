package com.t1.task4.usejwtexample.repository;

import com.t1.task4.usejwtexample.entity.RefreshToken;
import java.time.Duration;

public interface RefreshTokenRepository {
    Boolean saveRefreshToken(RefreshToken refreshToken, Duration expTime);
    RefreshToken getByValue(String refreshToken);
}
