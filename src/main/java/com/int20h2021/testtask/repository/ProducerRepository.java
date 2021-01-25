package com.int20h2021.testtask.repository;

import com.int20h2021.testtask.domain.json.common.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, String> {
}
