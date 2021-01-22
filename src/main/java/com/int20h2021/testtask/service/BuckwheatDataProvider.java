package com.int20h2021.testtask.service;

import com.int20h2021.testtask.domain.json.common.Items;
import org.springframework.util.MultiValueMap;

public interface BuckwheatDataProvider {
    Items getData(int offset, int limit, String sortBy, String sortDir);

    Items getFilteredData(int offset, int limit, String sortBy, String sortDir, MultiValueMap<String, String> filters);
}
