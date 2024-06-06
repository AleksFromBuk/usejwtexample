package com.t1.task4.usejwtexample.service;

import com.t1.task4.usejwtexample.entity.User;

import java.util.Optional;

public interface UserService {
    User create(User user);
    Optional<User> findByUsername(String username);
    Optional<User> findById(String id);
}
