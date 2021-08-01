package com.yhcy.aqc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public Long healthCheck() {
        return System.currentTimeMillis();
    }
}
