package com.t1.task4.usejwtexample.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class AppUserPrincipal implements Principal {
    private final String name;
    private final String id;
    private final List<String> roles;

    @Override
    public String getName() {
        return "";
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }
}
