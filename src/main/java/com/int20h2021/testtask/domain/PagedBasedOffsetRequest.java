package com.int20h2021.testtask.domain;

import lombok.Getter;

@Getter
public class PagedBasedOffsetRequest {
    private static final int ZAKAZ_LIMIT = 30;

    private final int limit;
    private final int offset;

    private int[] zakazPageNumbers;
    private int newOffset;

    public PagedBasedOffsetRequest(int offset, int limit) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }

        if (limit < 1) {
            throw new IllegalArgumentException("Limit must not be less than one!");
        }

        this.limit = limit;
        this.offset = offset;
    }

    public void convert() {
        double d1 = offset / ZAKAZ_LIMIT + 1;
        double d2 = (limit + offset) / ZAKAZ_LIMIT + 1;

        int i1 = (int) Math.floor(d1);
        int i2 = (int) Math.floor(d2);

        zakazPageNumbers = new int[i2 - i1 + 1];
        for (int i = 0; i < zakazPageNumbers.length; i++) {
            zakazPageNumbers[i] = i+i1;
        }

        newOffset = offset % ZAKAZ_LIMIT;
    }
}
