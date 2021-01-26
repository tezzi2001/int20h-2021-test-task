package com.int20h2021.testtask.domain.json.zakaz;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ZakazResponse implements Serializable {
    private Result[] results;
    private Filter[] filters;
    private int count;

    public boolean hasPayload() {
        return results != null;
    }
}