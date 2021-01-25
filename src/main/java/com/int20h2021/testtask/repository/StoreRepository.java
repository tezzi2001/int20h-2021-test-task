package com.int20h2021.testtask.repository;

import com.int20h2021.testtask.domain.json.common.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, String> {
}
