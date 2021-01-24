package com.int20h2021.testtask.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class UpdateRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private LocalDateTime recentScanDate;
    private short scanPeriod;

    public UpdateRequest(LocalDateTime recentScanDate, short scanPeriod) {
        this.recentScanDate = recentScanDate;
        this.scanPeriod = scanPeriod;
    }
}
