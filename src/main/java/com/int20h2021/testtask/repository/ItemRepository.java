package com.int20h2021.testtask.repository;

import com.int20h2021.testtask.domain.json.common.Item;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {
}
