package com.t1.task4.usejwtexample.controller;

import com.t1.task4.usejwtexample.configuration.SecurityConfiguration;
import com.t1.task4.usejwtexample.dto.PasswordTokenRequest;
import com.t1.task4.usejwtexample.dto.TokenResponse;
import com.t1.task4.usejwtexample.exception.CheckPasswordException;
import com.t1.task4.usejwtexample.security.SecurityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Import(SecurityConfiguration.class)
public class TokenControllerTest {

    @Test
    public void test_generate_token_with_valid_credentials() {
        SecurityService securityService = mock(SecurityService.class);
        TokenController tokenController = new TokenController(securityService);
        PasswordTokenRequest request = new PasswordTokenRequest();
        request.setUsername("validUser");
        request.setPassword("validPassword");
        SecurityService.TokenData tokenData = new SecurityService.TokenData("validToken", "validRefreshToken");
        when(securityService.processPasswordToken("validUser", "validPassword")).thenReturn(tokenData);
        ResponseEntity<TokenResponse> response = tokenController.password(request);
        assertEquals("validToken", response.getBody().getToken());
        assertEquals("validRefreshToken", response.getBody().getRefreshToken());
    }

    @Test
    public void test_handle_invalid_credentials() {
        SecurityService securityService = mock(SecurityService.class);
        TokenController tokenController = new TokenController(securityService);
        PasswordTokenRequest request = new PasswordTokenRequest();
        request.setUsername("invalidUser");
        request.setPassword("invalidPassword");
        when(securityService.processPasswordToken("invalidUser", "invalidPassword")).thenThrow(new CheckPasswordException());
        try {
            tokenController.password(request);
            fail("Expected CheckPasswordException to be thrown");
        } catch (CheckPasswordException e) {
            assertNotNull(e);
        }
    }

}
