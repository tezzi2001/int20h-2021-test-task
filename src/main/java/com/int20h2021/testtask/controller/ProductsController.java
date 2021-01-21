package com.int20h2021.testtask.controller;

import com.int20h2021.testtask.domain.json.common.Items;
import com.int20h2021.testtask.service.BuckwheatDataCollectionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductsController {
    private final BuckwheatDataCollectionService buckwheatDataCollectionService;

    @GetMapping("/buckwheat")
    public Items getAllBuckwheat() {
        return buckwheatDataCollectionService.getAllData();
    }
}
