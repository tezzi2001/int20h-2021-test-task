package com.int20h2021.testtask.repository;

import com.int20h2021.testtask.domain.json.common.item.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, String> {
}
