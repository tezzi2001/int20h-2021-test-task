package com.int20h2021.testtask.service.buckwheat;

import com.int20h2021.testtask.domain.OffsetBasedPageRequest;
import com.int20h2021.testtask.domain.entity.Item;
import com.int20h2021.testtask.domain.json.common.item.Data;
import com.int20h2021.testtask.domain.json.common.item.ItemJson;
import com.int20h2021.testtask.domain.json.common.item.entity.Producer;
import com.int20h2021.testtask.domain.json.common.item.entity.Store;
import com.int20h2021.testtask.repository.ItemRepository;
import com.int20h2021.testtask.repository.ProducerRepository;
import com.int20h2021.testtask.repository.StoreRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

        Pageable pageable;
        Pages pages;

        // Fix issue with filters and pagination. Should be removed in future releases
        if (offset != 0) {
            pageable = new OffsetBasedPageRequest(0, offset, getSort(sortBy, sortDir));
            pages = getPagesByFilters(pageable, filters);
            if (pages.totalCount <= offset) {
                pageable = new OffsetBasedPageRequest(0, pages.totalCount, getSort(sortBy, sortDir));
                pages = getPagesByFilters(pageable, filters);
                return toData(pages.pages, pages.totalCount);
            }
        }

        pageable = new OffsetBasedPageRequest(offset, limit, getSort(sortBy, sortDir));
        pages = filters.isEmpty() ?
                getAllPages(pageable) :
                getPagesByFilters(pageable, filters);


        return toData(pages.pages, pages.totalCount);
    }

    private Pages getAllPages(Pageable pageable) {
        return new Pages(
                itemRepository.findAll(pageable),
                (int) itemRepository.count()
        );
    }

    private Pages getPagesByFilters(Pageable pageable, MultiValueMap<String, String> filters) {
        List<String> storeFiltersIds;
        List<String> producersFiltersIds;
        String storeIdConst = STORE.getId();
        String producerIdConst = PRODUCER.getId();

        storeFiltersIds = filters.containsKey(storeIdConst) ?
                filters.get(storeIdConst) :
                storeRepository.findAll().stream()
                        .map(Store::getId)
                        .collect(Collectors.toList());
        producersFiltersIds = filters.containsKey(producerIdConst) ?
                filters.get(producerIdConst) :
                producerRepository.findAll().stream()
                        .map(Producer::getId).
                        collect(Collectors.toList());

        Iterable<Item> pages = itemRepository.findByStoreInAndProducerIn(
                storeRepository.findAllById(storeFiltersIds),
                producerRepository.findAllById(producersFiltersIds),
                pageable
        );
        int totalCount = itemRepository.countByStoreIdInAndProducerIdIn(
                storeFiltersIds,
                producersFiltersIds
        );

        return new Pages(pages, totalCount);
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

    private Data toData(Iterable<Item> items, int count) {
        ItemJson[] itemJsons = StreamSupport.stream(items.spliterator(), false)
                .map(Item::toJson)
                .toArray(ItemJson[]::new);
        return new Data(itemJsons, count);
    }

    @AllArgsConstructor
    private class Pages {
        private final Iterable<Item> pages;
        private final int totalCount;
    }
}
