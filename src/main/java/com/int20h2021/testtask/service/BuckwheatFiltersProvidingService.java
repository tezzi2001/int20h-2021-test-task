package com.int20h2021.testtask.service;

import com.int20h2021.testtask.domain.json.common.Filter;
import com.int20h2021.testtask.domain.json.common.FilterOption;
import com.int20h2021.testtask.domain.json.common.Items;
import com.int20h2021.testtask.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static com.int20h2021.testtask.constant.Filter.STORE;
import static com.int20h2021.testtask.constant.Store.*;

@Service("buckwheatDataProvidingService")
@RequiredArgsConstructor
public class BuckwheatFiltersProvidingService implements BuckwheatDataProvider {
    @Qualifier("buckwheatItemsProvidingService")
    private final BuckwheatDataProvider buckwheatItemsProvidingService;
    private final ItemRepository itemRepository;

    @Override
    public Items getData(int offset, int limit, String sortBy, String sortDir) {
        Items items = buckwheatItemsProvidingService.getData(offset, limit, sortBy, sortDir);
        addStoreFilterOption(items);

        return items;
    }

    @Override
    public Items getFilteredData(int offset, int limit, String sortBy, String sortDir, MultiValueMap<String, String> filters) {
        Items items = buckwheatItemsProvidingService.getFilteredData(offset, limit, sortBy, sortDir, filters);
        addStoreFilterOption(items);

        return items;
    }

    private void addStoreFilterOption(Items items) {
        List<FilterOption> filterOptions = new ArrayList<>(5);
        addFilterOptionIfStoreExists(ROZETKA, filterOptions);
        addFilterOptionIfStoreExists(ECOMARKET, filterOptions);
        addFilterOptionIfStoreExists(METRO, filterOptions);
        addFilterOptionIfStoreExists(NOVUS, filterOptions);
        addFilterOptionIfStoreExists(PROM, filterOptions);

        Filter filter1 = new Filter(STORE, "Магазин", filterOptions.toArray(new FilterOption[0]));
        items.setFilters(new Filter[]{filter1});
    }

    private void addFilterOptionIfStoreExists(String store, List<FilterOption> filterOptions) {
        if (itemRepository.existsByStore(store)) {
            filterOptions.add(new FilterOption(store, store));
        }
    }
}
