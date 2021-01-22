package com.int20h2021.testtask.service;

import com.int20h2021.testtask.domain.json.common.Data;
import org.springframework.util.MultiValueMap;

public interface BuckwheatDataProvider {
    Data getData(int offset, int limit, String sortBy, String sortDir);

    Data getFilteredData(int offset, int limit, String sortBy, String sortDir, MultiValueMap<String, String> filters);
}
