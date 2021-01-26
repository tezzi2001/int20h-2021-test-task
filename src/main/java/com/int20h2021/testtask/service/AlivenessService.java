package com.int20h2021.testtask.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class AlivenessService {
    private static final String WAKE_UP_ENDPOINT= "/service/wake-up-poll";

    private final RestTemplate restTemplate;
    private final ScheduledExecutorService scheduledExecutorService;

    @Value("${host}")
    private String host;

    public AlivenessService(RestTemplate restTemplate) {
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        this.scheduledExecutorService.scheduleAtFixedRate(this::wakeUp, 0, 25, TimeUnit.SECONDS);
        this.restTemplate = restTemplate;
    }

    public void wakeUp() {
        restTemplate.getForObject(host + WAKE_UP_ENDPOINT, Object.class);
    }
}
