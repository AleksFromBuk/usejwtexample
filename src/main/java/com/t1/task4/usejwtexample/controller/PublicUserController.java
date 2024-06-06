package com.t1.task4.usejwtexample.controller;


import com.t1.task4.usejwtexample.dto.CreateUserRequest;
import com.t1.task4.usejwtexample.entity.RoleType;
import com.t1.task4.usejwtexample.entity.User;
import com.t1.task4.usejwtexample.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/public/user")
@RequiredArgsConstructor
public class PublicUserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .username(request.getUsername())
                .roles(request.getRoles().stream()
                        .map(it -> RoleType.valueOf(it.toUpperCase())).collect(Collectors.toSet()))
                .build();
        userService.create(user);
        return ResponseEntity.ok("User successfully created!");
    }
}