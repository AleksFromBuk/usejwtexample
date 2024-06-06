package com.t1.task4.usejwtexample.security;

import com.t1.task4.usejwtexample.exception.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationManagerImpl implements AuthenticationManager {
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AppUserPrincipal principal = (AppUserPrincipal) authentication.getPrincipal();
        UserDetails user = userDetailsService.loadUserByUsername(principal.getName());
        if (!user.isEnabled()) {
            throw new AuthException("User disabled");
        }
        return authentication;
    }
}
