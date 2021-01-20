package com.int20h2021.testtask.domain.json.zakaz;

import java.io.Serializable;

@lombok.Data
public class ZakazResponse implements Serializable {
    private Result[] results;
}