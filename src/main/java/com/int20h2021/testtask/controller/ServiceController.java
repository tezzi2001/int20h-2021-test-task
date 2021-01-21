package com.int20h2021.testtask.controller;

import com.int20h2021.testtask.service.BuckwheatDataScanningService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
@AllArgsConstructor
public class ServiceController {
    private final BuckwheatDataScanningService buckwheatDataScanningService;

    @PutMapping ("/collect-data")
    public boolean collectData() {
        return buckwheatDataScanningService.scan();
    }
}
