package com.int20h2021.testtask.repository;

import com.int20h2021.testtask.domain.entity.Item;
import com.int20h2021.testtask.domain.json.common.item.entity.Producer;
import com.int20h2021.testtask.domain.json.common.item.entity.Store;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByStoreInAndProducerIn(List<Store> stores, List<Producer> producers, Pageable pageable);

    int countByStoreIdInAndProducerIdIn(List<String> storeIds, List<String> producerIds);
}
