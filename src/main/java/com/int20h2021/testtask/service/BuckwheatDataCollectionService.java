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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BuckwheatDataCollectionService {
    private static final String ASCENDING = "asc"; //by default
    private static final String DESCENDING = "desc";

    private final ItemRepository itemRepository;

    public Items getData(int offset, int limit) {
        Pageable pageable = new OffsetBasedPageRequest(offset, limit);
        Page<Item> pages = itemRepository.findAll(pageable);
        return toItems(pages);
    }

    public Items getData(int offset, int limit, String sortBy) {
        Pageable pageable = new OffsetBasedPageRequest(offset, limit, Sort.by(sortBy));
        Page<Item> pages = itemRepository.findAll(pageable);
        return toItems(pages);
    }

    public Items getData(int offset, int limit, String sortBy, String sortDir) {
        switch (sortDir) {
            case ASCENDING:
                return getData(offset, limit, Sort.by(sortBy).ascending());
            case DESCENDING:
                return getData(offset, limit, Sort.by(sortBy).descending());
            default: return getData(offset, limit, Sort.by(sortBy));
        }
    }

    private Items getData(int offset, int limit, Sort sort) {
        Pageable pageable = new OffsetBasedPageRequest(offset, limit, sort);
        Page<Item> pages = itemRepository.findAll(pageable);
        return toItems(pages);
    }

    private Items toItems(Iterable<Item> items) {
        List<Item> itemList = new ArrayList<>();
        items.forEach(itemList::add);
        Item[] itemArray = itemList.toArray(new Item[0]);
        return new Items(itemArray);
    }
}
