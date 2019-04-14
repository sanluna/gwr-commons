package com.sanluna.commons.controller;

import com.sanluna.commons.model.HealthChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RestController
@RequestMapping(value = "status", produces = {APPLICATION_JSON_VALUE, TEXT_PLAIN_VALUE}, consumes = {APPLICATION_JSON_VALUE, TEXT_PLAIN_VALUE})
public class HealthCheckerController {

    @Autowired
    private HealthChecker healthChecker;

    @GetMapping("/health")
    public HealthChecker checkIfUpJson() {
        return healthChecker.setRunningTime();
    }

}
