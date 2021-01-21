package com.int20h2021.testtask.domain.json.zakaz;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.int20h2021.testtask.domain.json.Transformable;
import com.int20h2021.testtask.domain.json.common.Item;
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

    public String getImg() {
        return img.getImg();
    }

    public float getPrice() {
        return price / 100;
    }

    @Override
    public Item toItem(String store) {
        return new Item(id, title, href, this.getImg(), this.getPrice(), store);
    }
}
