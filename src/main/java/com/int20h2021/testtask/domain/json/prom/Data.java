package com.int20h2021.testtask.domain.json.prom;

import java.io.Serializable;

@lombok.Data
public class Data implements Serializable {
    private Product[] productList;
}
