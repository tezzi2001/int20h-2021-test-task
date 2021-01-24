package com.int20h2021.testtask.constant;

import lombok.Getter;

public final class Filters {
    private Filters() {
    }

    public static final Filter STORE = new Filters.Filter("store", "Магазин");
    public static final Filter PRODUCER = new Filters.Filter("producer", "Виробник");

    @Getter
    public static class Filter {
        private final String id;
        private final String name;

        private Filter(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
