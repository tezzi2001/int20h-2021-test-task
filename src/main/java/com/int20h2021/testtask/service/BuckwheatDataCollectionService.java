package com.int20h2021.testtask.service;

import com.int20h2021.testtask.domain.json.common.Item;
import com.int20h2021.testtask.domain.json.common.Items;
import com.int20h2021.testtask.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuckwheatDataCollectionService {
    private final ItemRepository itemRepository;

    public Items getAllData() {
        return new Items(itemRepository.findAll().toArray(new Item[0]));
    }
}
