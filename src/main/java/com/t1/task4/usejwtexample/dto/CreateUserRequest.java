package com.t1.task4.usejwtexample.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CreateUserRequest {
    private String username;
    private String password;
    private String email;
    private Set<String> roles = new HashSet<>();
}
