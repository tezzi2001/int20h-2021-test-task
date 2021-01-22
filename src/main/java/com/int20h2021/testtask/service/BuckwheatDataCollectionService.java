package com.int20h2021.testtask.service;

import com.int20h2021.testtask.domain.OffsetBasedPageRequest;
import com.int20h2021.testtask.domain.json.common.Item;
import com.int20h2021.testtask.domain.json.common.Items;
import com.int20h2021.testtask.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BuckwheatDataCollectionService {
    private final ItemRepository itemRepository;

    public Items getData(int offset, int limit) {
        Pageable pageable = new OffsetBasedPageRequest(offset, limit);
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
