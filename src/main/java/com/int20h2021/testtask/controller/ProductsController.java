package com.int20h2021.testtask.controller;

import com.int20h2021.testtask.domain.json.common.Items;
import com.int20h2021.testtask.service.BuckwheatDataCollectionService;
import com.int20h2021.testtask.service.SearchAnyProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductsController {
    private final BuckwheatDataCollectionService buckwheatDataCollectionService;
    private final SearchAnyProductService searchAnyProductService;

    @GetMapping("/search")
    public Items search(@RequestParam(required = false) String query,
                        @RequestParam(required = false, defaultValue = "0") int offset,
                        @RequestParam(required = false, defaultValue = "20") int limit) {
        return query == null ?
                buckwheatDataCollectionService.getData(offset, limit) :
                searchAnyProductService.getRozetkaResponse(query);
    }
}
