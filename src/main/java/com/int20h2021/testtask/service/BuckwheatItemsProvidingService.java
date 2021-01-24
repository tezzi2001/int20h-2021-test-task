package com.int20h2021.testtask.service;

import com.int20h2021.testtask.domain.OffsetBasedPageRequest;
import com.int20h2021.testtask.domain.json.common.Data;
import com.int20h2021.testtask.domain.json.common.Item;
import com.int20h2021.testtask.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static com.int20h2021.testtask.constant.Filters.*;

@Service("buckwheatItemsProvidingService")
@RequiredArgsConstructor
public class BuckwheatItemsProvidingService implements BuckwheatDataProvider {
    private static final String ASCENDING = "asc";
    private static final String DESCENDING = "desc";

    private final ItemRepository itemRepository;

    @Override
    public Data getData(int offset, int limit, String sortBy, String sortDir, MultiValueMap<String, String> filters) {
        Pageable pageable = new OffsetBasedPageRequest(offset, limit, getSort(sortBy, sortDir));
        Iterable<Item> pages;
        int totalCount;

        if (filters.isEmpty()) {
            pages = itemRepository.findAll(pageable);
            totalCount = (int) itemRepository.count();
        } else {
            List<String> storeFilters;
            List<String> producersFilters;

            storeFilters = filters.containsKey(STORE.getId()) ?
                    filters.get(STORE.getId()) :
                    itemRepository.findDistinctStores();
            producersFilters = filters.containsKey(PRODUCER.getId()) ?
                    filters.get(PRODUCER.getId()) :
                    itemRepository.findDistinctProducers();

            pages = itemRepository.findByStoreInAndProducerIn(storeFilters, producersFilters, pageable);
            totalCount = itemRepository.countByStoreInAndProducerIn(storeFilters, producersFilters);
        }

        return toItems(pages, totalCount);
    }

    private Sort getSort(String sortBy, String sortDir) {
        Sort sort =
                sortBy == null ?
                Sort.unsorted() :
                Sort.by(sortBy);

        switch (sortDir) {
            case ASCENDING:
                return sort.ascending();
            case DESCENDING:
                return sort.descending();
            default:
                return sort;
        }
    }

    private Data toItems(Iterable<Item> items, int totalCount) {
        List<Item> itemList = new ArrayList<>();
        items.forEach(itemList::add);
        Item[] itemArray = itemList.toArray(new Item[0]);
        return new Data(itemArray, totalCount);
    }
}
