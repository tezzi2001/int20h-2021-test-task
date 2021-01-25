package com.int20h2021.testtask.repository;

import com.int20h2021.testtask.domain.entity.UpdateRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateRequestRepository extends JpaRepository<UpdateRequest, Integer> {
}
