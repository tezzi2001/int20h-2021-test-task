package com.int20h2021.testtask.controller;

import com.int20h2021.testtask.domain.json.common.Items;
import com.int20h2021.testtask.domain.json.zakaz.ZakazResponse;
import com.int20h2021.testtask.service.DataCollectionService;
import com.int20h2021.testtask.domain.json.rozetka.RozetkaResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class Controller {
    private final DataCollectionService dataCollectionService;

    @GetMapping("/rozetka")
    public RozetkaResponse rozetka() {
        return dataCollectionService.getRozetkaData();
    }

    @GetMapping("/metro")
    public ZakazResponse metro() {
        return dataCollectionService.getMetroData();
    }

    @GetMapping("/ecoMarket")
    public ZakazResponse ecoMarket() {
        return dataCollectionService.getEcoMarketData();
    }

    @GetMapping("/")
    public Items all() {
        return dataCollectionService.getAllData();
    }
}
