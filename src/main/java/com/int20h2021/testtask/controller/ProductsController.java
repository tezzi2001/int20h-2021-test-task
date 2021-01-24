package com.int20h2021.testtask.controller;

import com.int20h2021.testtask.domain.json.common.Data;
import com.int20h2021.testtask.service.BuckwheatDataProvider;
import com.int20h2021.testtask.service.SearchAnyProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductsController {
    @Qualifier("buckwheatDataProvidingService")
    private final BuckwheatDataProvider buckwheatDataProvidingService;
    private final SearchAnyProductService searchAnyProductService;

    @GetMapping("/search")
    public Data search(@RequestParam(required = false) String query,
                       @RequestParam(required = false, defaultValue = "0") int offset,
                       @RequestParam(required = false, defaultValue = "20") int limit,
                       @RequestParam(required = false, defaultValue = "price") String sortBy,
                       @RequestParam(required = false, defaultValue = "asc") String sortDir,
                       @RequestParam MultiValueMap<String, String> filters) {
        deleteNonFilterKeys(filters);
        if (query == null) {
            return buckwheatDataProvidingService.getData(offset, limit, sortBy, sortDir, filters);
        } else {
            return searchAnyProductService.getRozetkaResponse(query);
        }
    }

    private void deleteNonFilterKeys(MultiValueMap<String, String> filters) {
        filters.remove("query");
        filters.remove("offset");
        filters.remove("limit");
        filters.remove("sortBy");
        filters.remove("sortDir");
    }
}
