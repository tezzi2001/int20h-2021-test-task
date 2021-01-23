package com.int20h2021.testtask.constant;

public final class Store {
    private Store() {
    }

    public static final int METRO_ID = 48215611;
    public static final int ECOMARKET_ID = 48280214;
    public static final int NOVUS_ID = 48201070;
    public static final int ASHAN_ID = 48246401;
    public static final int VARUS_ID = 48241001;
    public static final int CITYMARKET_ID = 48250029;
    public static final int MEGAMARKET_ID = 48267601;
    public static final int FURSHET_ID = 48215518;

    public static final String METRO = "Metro";
    public static final String ECOMARKET = "Екомаркет";
    public static final String NOVUS = "Novus";
    public static final String ASHAN = "Ашан";
    public static final String VARUS = "Varus";
    public static final String CITYMARKET = "CityMarket";
    public static final String MEGAMARKET = "MegaMarket";
    public static final String FURSHET = "Фуршет";

    public static final String ZAKAZ_REQUEST_URL = "https://stores-api.zakaz.ua/stores/{storeId}/products/search/?q=крупа+гречана";

    public static final String ZAKAZ_COMMON_REQUEST_URL = "https://stores-api.zakaz.ua/stores/{storeId}/products/search/?q={query}";
}
