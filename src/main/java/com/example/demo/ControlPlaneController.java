package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@EnableAsync
@RestController
@RequiredArgsConstructor
public class ControlPlaneController {
    private final ApplicationEventPublisher eventPublisher;

    @GetMapping("/block")
    public String block() {
        AvailabilityChangeEvent.publish(eventPublisher, this, ReadinessState.REFUSING_TRAFFIC);
        return "Blocked requests";
    }

    @GetMapping("/turn-off")
    public String turnOff() {
        AvailabilityChangeEvent.publish(eventPublisher, this, LivenessState.BROKEN);
        return "Broken!!";
    }

    @Async
    @EventListener
    public void onReadinessStateChanged(AvailabilityChangeEvent<ReadinessState> readiness) throws InterruptedException {
        log.info("Readiness state is changed to " + readiness.getState());
        if (ReadinessState.REFUSING_TRAFFIC == readiness.getState()) {
            Thread.sleep(5000L);
            AvailabilityChangeEvent.publish(eventPublisher, this, ReadinessState.ACCEPTING_TRAFFIC);
        }
    }

    @Async
    @EventListener
    public void onLivenessStateChanged(AvailabilityChangeEvent<LivenessState> liveness) throws InterruptedException {
        log.info("Liveness state is changed to " + liveness.getState());
        if (LivenessState.BROKEN == liveness.getState()) {
            Thread.sleep(5000L);
            AvailabilityChangeEvent.publish(eventPublisher, this, LivenessState.CORRECT);
        }
    }
}
