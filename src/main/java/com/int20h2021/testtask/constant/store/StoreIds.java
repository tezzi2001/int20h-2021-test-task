package com.int20h2021.testtask.constant.store;

enum StoreIds {
    METRO_ID (48215611),
    ECOMARKET_ID (48280214),
    NOVUS_ID (48201070),
    ASHAN_ID (48246401),
    VARUS_ID (48241001),
    CITYMARKET_ID (48250029),
    MEGAMARKET_ID (48267601),
    FURSHET_ID (48215518);

    private final int id;

    StoreIds(int id) {
        this.id = id;
    }

    public int toInt() {
        return id;
    }
}
