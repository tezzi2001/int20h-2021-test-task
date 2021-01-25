package com.int20h2021.testtask.service;

import com.int20h2021.testtask.domain.json.common.chart.entity.PriceChart;
import com.int20h2021.testtask.repository.PriceChartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BuckwheatChartService {
    private final PriceChartRepository priceChartRepository;

    public List<PriceChart> getPriceChart(LocalDate dateBefore, LocalDate dateAfter) {
        return priceChartRepository.findAllByDateBetweenOrderByDateAsc(dateBefore, dateAfter);
    }
}
