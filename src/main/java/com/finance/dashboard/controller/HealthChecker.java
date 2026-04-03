package com.finance.dashboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthChecker {

    @GetMapping("/health-checker")
    public String healthChecker(){
        return "Application is running";
    }
}
