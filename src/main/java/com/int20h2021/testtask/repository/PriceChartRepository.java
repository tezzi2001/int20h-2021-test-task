package com.int20h2021.testtask.repository;

import com.int20h2021.testtask.domain.json.common.chart.entity.PriceChart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PriceChartRepository extends JpaRepository<PriceChart, Long> {
    List<PriceChart> findAllByDateBetweenOrderByDateAsc(LocalDate dateBefore, LocalDate dateAfter);

    Optional<PriceChart> findByDate(LocalDate date);
}
