package com.jaesay.demokubernetes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    Logger logger = LoggerFactory.getLogger(SampleController.class);

    @GetMapping("/health")
    public String health() {
        return "health check";
    }

    @GetMapping("/slow")
    public String slow() throws InterruptedException {
        logger.info("got the request");
        Thread.sleep(10000);
        return "slow";
    }
}
