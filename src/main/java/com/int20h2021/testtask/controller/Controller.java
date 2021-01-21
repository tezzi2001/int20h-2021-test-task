package com.int20h2021.testtask.controller;

import com.int20h2021.testtask.domain.json.common.Items;
import com.int20h2021.testtask.service.DataCollectionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class Controller {
    private final DataCollectionService dataCollectionService;

    @GetMapping("/")
    public Items all() {
        return dataCollectionService.getAllData();
    }
}
