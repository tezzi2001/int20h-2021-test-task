package com.int20h2021.testtask.domain.json.zakaz;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.int20h2021.testtask.domain.entity.Item;
import com.int20h2021.testtask.domain.json.Transformable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class Result extends Transformable implements Serializable {
    @JsonProperty("sku") private long id;
    private String title;
    @JsonProperty("web_url") private String href;
    private Image img;
    private float price;
    private int weight;
    private Producer producer;

    public String getImg() {
        return img.getImg();
    }

    public float getPrice() {
        return price / 100;
    }

    @Override
    public Item toItem(String store) {
        return new Item(id, title, href, getImg(), getPrice(), store, weight, producer.getTrademark());
    }
}
