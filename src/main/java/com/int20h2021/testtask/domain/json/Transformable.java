package com.int20h2021.testtask.domain.json;

import com.int20h2021.testtask.domain.json.common.entity.Item;

public abstract class Transformable {
    public abstract Item toItem(String store);
}
