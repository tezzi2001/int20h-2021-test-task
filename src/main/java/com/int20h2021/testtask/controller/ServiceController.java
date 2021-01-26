package com.int20h2021.testtask.controller;

import com.int20h2021.testtask.service.buckwheat.BuckwheatDataCollectionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
@AllArgsConstructor
public class ServiceController {
    private final BuckwheatDataCollectionService buckwheatDataCollectionService;

    @GetMapping("/collect-data")
    public boolean collectData() {
        return buckwheatDataCollectionService.collect();
    }

    // Stub method for waking up
    @GetMapping("/wake-up-poll")
    public void pollToWakeUp() {
    }
}
