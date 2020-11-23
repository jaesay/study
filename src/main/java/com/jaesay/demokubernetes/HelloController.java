package com.jaesay.demokubernetes;

import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final ApplicationAvailability availability;

    private final LocalHostService localHostService;

    public HelloController(ApplicationAvailability availability, LocalHostService localHostService) {
        this.availability = availability;
        this.localHostService = localHostService;
    }

    @GetMapping("/hello")
    public String hello() {
        return String.format("Liveness: %s, Readiness: %s, %s",
                availability.getLivenessState(),
                availability.getReadinessState(),
                localHostService.getLocalHostInfo());
    }
}
