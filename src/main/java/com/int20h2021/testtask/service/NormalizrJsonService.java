package com.int20h2021.testtask.service;

import com.int20h2021.testtask.domain.json.Transformable;
import com.int20h2021.testtask.domain.json.common.Item;
import com.int20h2021.testtask.domain.json.prom.PromResponse;
import com.int20h2021.testtask.domain.json.rozetka.RozetkaResponse;
import com.int20h2021.testtask.domain.json.zakaz.ZakazResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NormalizrJsonService {
    public List<Item> normalize(RozetkaResponse response, String store) {
        return normalize(response.getData().getGoods(), store);
    }

    public List<Item> normalize(ZakazResponse response, String store) {
        return normalize(response.getResults(), store);
    }

    public List<Item> normalize(PromResponse response, String store) {
        return normalize(response.getData().getProductList(), store);
    }

    private List<Item> normalize(Transformable[] transformables, String store) {
        List<Item> items = new ArrayList<>();

        for (Transformable product : transformables) {
            items.add(product.toItem(store));
        }

        return items;
    }
}
