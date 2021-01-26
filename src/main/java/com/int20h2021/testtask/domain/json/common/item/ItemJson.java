package com.int20h2021.testtask.domain.json.common.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemJson implements Serializable {
    private long id;
    private String title;
    private String href;
    private String img;
    private float price;
    private int weight;
    private float pricePerKg;
    private String store;
    private String producer;
}
