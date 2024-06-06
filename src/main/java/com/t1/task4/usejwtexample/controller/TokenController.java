package com.t1.task4.usejwtexample.controller;

import com.t1.task4.usejwtexample.dto.PasswordTokenRequest;
import com.t1.task4.usejwtexample.dto.RefreshTokenRequest;
import com.t1.task4.usejwtexample.dto.TokenResponse;
import com.t1.task4.usejwtexample.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/public/token")
@RequiredArgsConstructor
public class TokenController {

    private final SecurityService securityService;

    @PostMapping("/password")
    public ResponseEntity<TokenResponse> password(@RequestBody PasswordTokenRequest request) {
        var tokenData = securityService.processPasswordToken(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(new TokenResponse(tokenData.token(), tokenData.refreshToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestBody RefreshTokenRequest request) {
        var tokenData = securityService.processRefreshToken(request.getRefreshToken());
        return ResponseEntity.ok(new TokenResponse(tokenData.token(), tokenData.refreshToken()));
    }
}