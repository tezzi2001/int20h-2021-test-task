package com.int20h2021.testtask.domain.json.prom;

import com.int20h2021.testtask.domain.json.Transformable;
import com.int20h2021.testtask.domain.json.common.Item;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class Product extends Transformable implements Serializable {
    private long id;
    private String name;
    private String urlForProductCatalog;
    private String imageGallery;
    private float price;

    @Override
    public Item toItem(String store) {
        return new Item(id, name, urlForProductCatalog, imageGallery, price, store);
    }
}
