package com.int20h2021.testtask.domain.json.common.chart.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
public class PriceChart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long id;

    @Column(unique = true)
    private LocalDate date;

    private float pricePerKg;

    public PriceChart(LocalDate date, float pricePerKg) {
        this.date = date;
        this.pricePerKg = pricePerKg;
    }
}
