package com.int20h2021.testtask.service;

import com.int20h2021.testtask.domain.OffsetBasedPageRequest;
import com.int20h2021.testtask.domain.json.common.Item;
import com.int20h2021.testtask.domain.json.common.Items;
import com.int20h2021.testtask.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.*;

import static com.int20h2021.testtask.constant.Filter.STORE;

@Service("buckwheatItemsProvidingService")
@RequiredArgsConstructor
public class BuckwheatDataProvidingService implements BuckwheatDataProvider {
    private static final String ASCENDING = "asc"; //by default
    private static final String DESCENDING = "desc";

    private final ItemRepository itemRepository;

    public Items getData(int offset, int limit) {
        return getData(offset, limit, null, "");
    }

    public Items getData(int offset, int limit, String sortBy) {
        return getData(offset, limit, sortBy, "");
    }

    @Override
    public Items getData(int offset, int limit, String sortBy, String sortDir) {
        return getData(offset, limit, getSort(sortBy, sortDir));
    }

    @Override
    public Items getFilteredData(int offset, int limit, String sortBy, String sortDir, MultiValueMap<String, String> filters) {
        Pageable pageable = new OffsetBasedPageRequest(offset, limit, getSort(sortBy, sortDir));
        List<Item> pages;

        if (filters.containsKey(STORE)) {
            pages = itemRepository.findByStoreIn(filters.get(STORE), pageable);
            return toItems(pages);
        }
        return null;
    }

    private Items getData(int offset, int limit, Sort sort) {
        Pageable pageable = new OffsetBasedPageRequest(offset, limit, sort);
        Page<Item> pages = itemRepository.findAll(pageable);
        return toItems(pages);
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

    private Items toItems(Iterable<Item> items) {
        List<Item> itemList = new ArrayList<>();
        items.forEach(itemList::add);
        Item[] itemArray = itemList.toArray(new Item[0]);
        return new Items(itemArray);
    }
}
