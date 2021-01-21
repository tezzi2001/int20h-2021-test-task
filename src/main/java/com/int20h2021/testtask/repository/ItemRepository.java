package com.int20h2021.testtask.repository;

import com.int20h2021.testtask.domain.json.common.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
