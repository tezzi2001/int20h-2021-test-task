package com.int20h2021.testtask.service;

import com.int20h2021.testtask.domain.json.common.entity.Deliverable;
import com.int20h2021.testtask.domain.json.common.Data;
import com.int20h2021.testtask.domain.json.common.Filter;
import com.int20h2021.testtask.domain.json.common.FilterOption;
import com.int20h2021.testtask.repository.ProducerRepository;
import com.int20h2021.testtask.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static com.int20h2021.testtask.constant.Filters.PRODUCER;
import static com.int20h2021.testtask.constant.Filters.STORE;

@Service("buckwheatDataProvidingService")
@RequiredArgsConstructor
public class BuckwheatFiltersProvidingService implements BuckwheatDataProvider {
    @Qualifier("buckwheatItemsProvidingService")
    private final BuckwheatDataProvider buckwheatItemsProvidingService;
    private final ProducerRepository producerRepository;
    private final StoreRepository storeRepository;

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
        List<Deliverable> stores = new ArrayList<>(storeRepository.findAll());
        List<FilterOption> storeFilterOptions = getFilterOptionsOfColumn(stores);
        return new Filter(
                STORE.getId(),
                STORE.getName(),
                storeFilterOptions.toArray(new FilterOption[0])
        );
    }

    private Filter getProducerFilterOption() {
        List<Deliverable> producers = new ArrayList<>(producerRepository.findAll());
        List<FilterOption> producerFilterOptions = getFilterOptionsOfColumn(producers);
        return new Filter(
                PRODUCER.getId(),
                PRODUCER.getName(),
                producerFilterOptions.toArray(new FilterOption[0])
        );
    }

    private List<FilterOption> getFilterOptionsOfColumn(List<Deliverable> deliverables) {
        List<FilterOption> storeFilterOptions = new ArrayList<>();
        for (Deliverable deliverable : deliverables) {
            storeFilterOptions.add(
                    new FilterOption(deliverable.getId(),
                    deliverable.getName()
            ));
        }
        return storeFilterOptions;
    }
}
