package com.int20h2021.testtask.service;

import com.int20h2021.testtask.domain.json.common.Data;
import com.int20h2021.testtask.domain.json.common.Filter;
import com.int20h2021.testtask.domain.json.common.FilterOption;
import com.int20h2021.testtask.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static com.int20h2021.testtask.constant.Filters.*;

@Service("buckwheatDataProvidingService")
@RequiredArgsConstructor
public class BuckwheatFiltersProvidingService implements BuckwheatDataProvider {
    @Qualifier("buckwheatItemsProvidingService")
    private final BuckwheatDataProvider buckwheatItemsProvidingService;
    private final ItemRepository itemRepository;

    @Override
    public Data getData(int offset, int limit, String sortBy, String sortDir, MultiValueMap<String, String> filters) {
        Data data = buckwheatItemsProvidingService.getData(offset, limit, sortBy, sortDir, filters);

        data.setFilters(new Filter[] {
                getStoreFilterOption(),
                getProducerFilterOption()
        });

        return data;
    }

    private Filter getStoreFilterOption() {
        List<String> stores = itemRepository.findDistinctStores();
        List<FilterOption> storeFilterOptions = getFilterOptionsOfColumn(stores, STORE.getId());
        return new Filter(STORE.getId(), STORE.getName(), storeFilterOptions.toArray(new FilterOption[0]));
    }

    private Filter getProducerFilterOption() {
        List<String> producers = itemRepository.findDistinctProducers();
        List<FilterOption> producerFilterOptions = getFilterOptionsOfColumn(producers, PRODUCER.getId());
        return new Filter(PRODUCER.getId(), PRODUCER.getName(), producerFilterOptions.toArray(new FilterOption[0]));
    }

    private List<FilterOption> getFilterOptionsOfColumn(List<String> columnValues, String type) {
        List<FilterOption> storeFilterOptions = new ArrayList<>();
        for (String value : columnValues) {
            storeFilterOptions.add(new FilterOption(value, value, type));
        }
        return storeFilterOptions;
    }
}
