package com.int20h2021.testtask.repository;

import com.int20h2021.testtask.domain.json.common.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProducerRepository extends JpaRepository<Producer, String> {
}
