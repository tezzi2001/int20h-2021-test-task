package com.int20h2021.testtask.repository;

import com.int20h2021.testtask.domain.json.common.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByStoreIn(List<String> store, Pageable pageable);
    boolean existsByStore(String store);
}
