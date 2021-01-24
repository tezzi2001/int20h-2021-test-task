package com.int20h2021.testtask.constant.store;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public final class Stores {
    private final Map<Integer, String> storesMap;

    public Stores () {
        storesMap = new HashMap<>();
        StoreIds[] ids = StoreIds.values();
        StoreNames[] names = StoreNames.values();

        if (ids.length != names.length) {
            throw new IllegalStateException("the number of store ids not equals to number of store names. Check enums");
        }

        int length = ids.length;
        for (int i = 0; i < length; i++) {
            storesMap.put(
                    ids[i].toInt(),
                    names[i].toString()
            );
        }
    }
}
