package com.example.common.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class BaseHealthController {

    @Value("${spring.application.name:Unknown Service}")
    private String applicationName;

    @GetMapping
    public Map<String, Object> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("application", applicationName);
        health.put("version", "1.0.0");
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        return health;
    }
}

