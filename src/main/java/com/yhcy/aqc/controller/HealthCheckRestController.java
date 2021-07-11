package com.yhcy.aqc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class HealthCheckRestController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping(path = "_hcheck")
    public Long healthCheck() {
        log.info("call healthCheck");
        return System.currentTimeMillis();
    }
}
