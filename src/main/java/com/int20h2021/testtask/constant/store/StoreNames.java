package com.int20h2021.testtask.constant.store;

enum StoreNames {
    METRO ("Metro"),
    ECOMARKET ("Екомаркет"),
    NOVUS ("Novus"),
    ASHAN ("Ашан"),
    VARUS ("Varus"),
    CITYMARKET ("CityMarket"),
    MEGAMARKET ("MegaMarket"),
    FURSHET ("Фуршет");
    
    private final String name;

    StoreNames(String name) {
        this.name  = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
