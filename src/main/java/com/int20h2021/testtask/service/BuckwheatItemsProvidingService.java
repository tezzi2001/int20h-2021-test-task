package com.int20h2021.testtask.service;

import com.int20h2021.testtask.domain.OffsetBasedPageRequest;
import com.int20h2021.testtask.domain.json.common.*;
import com.int20h2021.testtask.domain.json.common.entity.Item;
import com.int20h2021.testtask.domain.json.common.entity.Producer;
import com.int20h2021.testtask.domain.json.common.entity.Store;
import com.int20h2021.testtask.repository.ItemRepository;
import com.int20h2021.testtask.repository.ProducerRepository;
import com.int20h2021.testtask.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.int20h2021.testtask.constant.Filters.PRODUCER;
import static com.int20h2021.testtask.constant.Filters.STORE;

@Service("buckwheatItemsProvidingService")
@RequiredArgsConstructor
public class BuckwheatItemsProvidingService implements BuckwheatDataProvider {
    private static final String ASCENDING = "asc";
    private static final String DESCENDING = "desc";

    private final ProducerRepository producerRepository;
    private final StoreRepository storeRepository;
    private final ItemRepository itemRepository;
    private final BuckwheatDataUpdateService buckwheatDataUpdateService;

    @Override
    public Data getData(int offset, int limit, String sortBy, String sortDir, MultiValueMap<String, String> filters) {
        checkForUpdate();
        Pageable pageable = new OffsetBasedPageRequest(offset, limit, getSort(sortBy, sortDir));
        Iterable<Item> pages;
        int totalCount;

        if (filters.isEmpty()) {
            pages = itemRepository.findAll(pageable);
            totalCount = (int) itemRepository.count();
        } else {
            List<String> storeFiltersIds;
            List<String> producersFiltersIds;
            String storeIdConst = STORE.getId();
            String producerIdConst = PRODUCER.getId();

            storeFiltersIds = filters.containsKey(storeIdConst) ?
                    filters.get(storeIdConst) :
                    storeRepository.findAll().stream().map(Store::getId).collect(Collectors.toList());
            producersFiltersIds = filters.containsKey(producerIdConst) ?
                    filters.get(producerIdConst) :
                    producerRepository.findAll().stream().map(Producer::getId).collect(Collectors.toList());

            pages = itemRepository.findByStoreInAndProducerIn(
                    storeRepository.findAllById(storeFiltersIds),
                    producerRepository.findAllById(producersFiltersIds),
                    pageable
            );
            totalCount = itemRepository.countByStoreIdInAndProducerIdIn(
                    storeFiltersIds,
                    producersFiltersIds
            );
        }

        return toItems(pages, totalCount);
    }

    private void checkForUpdate() {
        if (buckwheatDataUpdateService.needForUpdate()) {
            buckwheatDataUpdateService.update();
        }
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
        items.forEach(Item::parse);
        Item[] itemArray = itemList.toArray(new Item[0]);
        return new Data(itemArray, totalCount);
    }
}
