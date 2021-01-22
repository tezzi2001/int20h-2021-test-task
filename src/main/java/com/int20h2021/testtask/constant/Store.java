package com.int20h2021.testtask.constant;

public final class Store {
    private Store() {
    }

    public static final int METRO_ID = 48215611;
    public static final int ECOMARKET_ID = 48280214;
    public static final int NOVUS_ID = 48201070;

    public static final String METRO = "Metro";
    public static final String ECOMARKET = "Екомаркет";
    public static final String ROZETKA = "Rozetka";
    public static final String NOVUS = "Novus";
    public static final String PROM = "Prom";

    public static final String ZAKAZ_REQUEST_URL = "https://stores-api.zakaz.ua/stores/{storeId}/products/search/?q=крупа+гречана";
    public static final String ROZETKA_REQUEST_URL = "https://search.rozetka.com.ua/search/api/v4/?front-type=xl&text=крупа+гречана&lang=ua";
    public static final String PROM_REQUEST_URL = "https://prom.ua/graphql";

    public static final String ROZETKA_COMMON_REQUEST_URL = "https://search.rozetka.com.ua/ua/search/api/v4/?front-type=xl&text={query}&lang=ua";
}
