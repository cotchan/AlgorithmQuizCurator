package com.yhcy.aqc.controller;

import com.yhcy.aqc.service.HealthCheckService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class HealthCheckRestController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final HealthCheckService healthCheckService;

    @Async
    @GetMapping(path = "_hcheck")
    public CompletableFuture<Long> healthCheck() throws InterruptedException {
        logger.info("HealthCheckRestController::healthCheck {}", Thread.currentThread().getName());
        return CompletableFuture.completedFuture(healthCheckService.healthCheck());
    }
}
