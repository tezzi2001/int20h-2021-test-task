package com.int20h2021.testtask.domain.json.rozetka;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.int20h2021.testtask.domain.json.Transformable;
import com.int20h2021.testtask.domain.json.common.Item;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class Good extends Transformable implements Serializable {
    private long id;
    private String title;
    private String href;
    @JsonProperty("image_main") private String img;
    private float price;

    @Override
    public Item toItem(String store) {
        return new Item(id, title, href, img, price, store);
    }
}
