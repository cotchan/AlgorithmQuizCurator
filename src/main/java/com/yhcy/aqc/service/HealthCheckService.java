package com.yhcy.aqc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static java.lang.Thread.sleep;

@Service
public class HealthCheckService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public Long healthCheck() throws InterruptedException {
        sleep(5000);
        log.info("HealthCheckService::healthCheck {}", Thread.currentThread().getName());
        return System.currentTimeMillis();
    }
}
