package com.int20h2021.testtask.constant;

public final class Urls {
    private Urls() {
    }

    public static final String ZAKAZ_REQUEST_URL = "https://stores-api.zakaz.ua/stores/{storeId}/products/search/?q=крупа+гречана";
    public static final String ZAKAZ_COMMON_REQUEST_URL = "https://stores-api.zakaz.ua/stores/{storeId}/products/search/?q={query}";
}
