package com.t1.task4.usejwtexample.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping
    public String manager() {
        return "Manager";
    }
}