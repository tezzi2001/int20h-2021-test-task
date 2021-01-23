package com.int20h2021.testtask.service;

import com.int20h2021.testtask.domain.json.common.Filter;
import com.int20h2021.testtask.domain.json.common.FilterOption;
import com.int20h2021.testtask.domain.json.common.Data;
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
    public Data getData(int offset, int limit, String sortBy, String sortDir) {
        Data data = buckwheatItemsProvidingService.getData(offset, limit, sortBy, sortDir);
        addStoreFilterOption(data);

        return data;
    }

    @Override
    public Data getFilteredData(int offset, int limit, String sortBy, String sortDir, MultiValueMap<String, String> filters) {
        Data data = buckwheatItemsProvidingService.getFilteredData(offset, limit, sortBy, sortDir, filters);
        addStoreFilterOption(data);

        return data;
    }

    private void addStoreFilterOption(Data data) {
        List<FilterOption> filterOptions = new ArrayList<>(5);
        addFilterOptionIfStoreExists(ECOMARKET, filterOptions);
        addFilterOptionIfStoreExists(METRO, filterOptions);
        addFilterOptionIfStoreExists(NOVUS, filterOptions);
        addFilterOptionIfStoreExists(ASHAN, filterOptions);
        addFilterOptionIfStoreExists(VARUS, filterOptions);
        addFilterOptionIfStoreExists(CITYMARKET, filterOptions);
        addFilterOptionIfStoreExists(MEGAMARKET, filterOptions);
        addFilterOptionIfStoreExists(FURSHET, filterOptions);

        Filter filter1 = new Filter(STORE, "Магазин", filterOptions.toArray(new FilterOption[0]));
        data.setFilters(new Filter[]{filter1});
    }

    private void addFilterOptionIfStoreExists(String store, List<FilterOption> filterOptions) {
        if (itemRepository.existsByStore(store)) {
            filterOptions.add(new FilterOption(store, store));
        }
    }
}
