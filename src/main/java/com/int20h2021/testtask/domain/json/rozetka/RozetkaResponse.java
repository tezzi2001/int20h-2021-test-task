package com.int20h2021.testtask.domain.json.rozetka;

import java.io.Serializable;

@lombok.Data
public class RozetkaResponse implements Serializable {
    private Data data;

    public boolean hasPayload() {
        return getData() != null && getData().getGoods() != null;
    }
}
