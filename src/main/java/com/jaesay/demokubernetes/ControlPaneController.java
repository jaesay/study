package com.jaesay.demokubernetes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControlPaneController {

    private final ApplicationEventPublisher eventPublisher;

    private final LocalHostService localHostService;

    Logger logger = LoggerFactory.getLogger(ControlPaneController.class);

    public ControlPaneController(ApplicationEventPublisher eventPublisher, LocalHostService localHostService) {
        this.eventPublisher = eventPublisher;
        this.localHostService = localHostService;
    }

    @GetMapping("/block")
    public String block() {
        AvailabilityChangeEvent.publish(eventPublisher, this, ReadinessState.REFUSING_TRAFFIC);
        return "Blocked requests " + localHostService.getLocalHostInfo();
    }

    @GetMapping("turnoff")
    public String turnOff() {
        AvailabilityChangeEvent.publish(eventPublisher, this, LivenessState.BROKEN);
        return "Broken " + localHostService.getLocalHostInfo();
    }

    @Async
    @EventListener
    public void onStateChanged(AvailabilityChangeEvent<ReadinessState> readiness) throws InterruptedException {
        logger.info("State is changed to " + readiness.getState());
        if (readiness.getState() == ReadinessState.ACCEPTING_TRAFFIC) {
            Thread.sleep(15000L);
            AvailabilityChangeEvent.publish(eventPublisher, this, ReadinessState.ACCEPTING_TRAFFIC);
        }
    }
}
