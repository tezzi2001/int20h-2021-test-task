package com.int20h2021.testtask.repository;

import com.int20h2021.testtask.domain.json.common.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByStoreInAndProducerIn(List<String> stores, List<String> producers, Pageable pageable);

    @Query(value = "SELECT DISTINCT producer FROM int20h2021.item", nativeQuery = true)
    List<String> findDistinctProducers();

    @Query(value = "SELECT DISTINCT store FROM int20h2021.item", nativeQuery = true)
    List<String> findDistinctStores();

    int countByStoreInAndProducerIn(List<String> stores, List<String> producers);
}
