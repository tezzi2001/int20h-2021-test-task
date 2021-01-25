package com.int20h2021.testtask.domain.json.common.chart;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.int20h2021.testtask.domain.json.common.chart.entity.PriceChart;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ChartData implements Serializable {
    @JsonProperty("priceChart")
    private PriceChart[] priceCharts;
}
