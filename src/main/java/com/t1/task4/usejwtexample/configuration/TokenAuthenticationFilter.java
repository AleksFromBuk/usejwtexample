package com.t1.task4.usejwtexample.configuration;

import com.t1.task4.usejwtexample.security.SecurityAuthConverter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import java.io.IOException;

//public class TokenAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {
//
//    private final SecurityAuthConverter securityAuthConverter;
//
//    public TokenAuthenticationFilter(SecurityAuthConverter securityAuthConverter) {
//        this.securityAuthConverter = securityAuthConverter;
//    }
//
//    @Override
//    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
//        Authentication auth = securityAuthConverter.convert(request);
//        if (auth != null) {
//            SecurityContextHolder.getContext().setAuthentication(auth);
//            return auth.getPrincipal();
//        }
//        return null;
//    }
//
//    @Override
//    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
//        return "";
//    }
//
//    //@Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        super.doFilter(request, response, chain);
//        chain.doFilter(request, response);
//    }
//}


//public class TokenAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {
//
//    private final SecurityAuthConverter securityAuthConverter;
//
//    public TokenAuthenticationFilter(SecurityAuthConverter securityAuthConverter) {
//        this.securityAuthConverter = securityAuthConverter;
//    }
//
//    @Override
//    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
//        Authentication auth = securityAuthConverter.convert(request);
//        if (auth != null) {
//            SecurityContextHolder.getContext().setAuthentication(auth);
//            return auth.getPrincipal();
//        }
//        return null;
//    }
//
//    @Override
//    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
//        return "";
//    }
//
//    //@Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        super.doFilter(request, response, chain);
//        chain.doFilter(request, response);
//    }
//}

import org.springframework.web.filter.OncePerRequestFilter;



public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final SecurityAuthConverter securityAuthConverter;

    public TokenAuthenticationFilter(SecurityAuthConverter securityAuthConverter) {
        this.securityAuthConverter = securityAuthConverter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication auth = securityAuthConverter.convert(request);
        if (auth != null) {
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
}
