package com.int20h2021.testtask.domain.json.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Item implements Serializable {
    private long id;
    private String title;
    private String href;
    private String img;
    private float price;
}
