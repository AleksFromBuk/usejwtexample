package com.t1.task4.usejwtexample.dto;

import lombok.Data;

@Data
public class PasswordTokenRequest {
    private String username;
    private String password;
}
