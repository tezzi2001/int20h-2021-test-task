package com.int20h2021.testtask.service.buckwheat;

import com.int20h2021.testtask.domain.json.common.item.Data;
import org.springframework.util.MultiValueMap;

public interface BuckwheatDataProvider {
    Data getData(int offset, int limit, String sortBy, String sortDir, MultiValueMap<String, String> filters);
}
