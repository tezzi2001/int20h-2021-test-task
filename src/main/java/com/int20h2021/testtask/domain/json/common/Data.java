package com.int20h2021.testtask.domain.json.common;

import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@lombok.Data
@RequiredArgsConstructor
public class Data implements Serializable {
    private final Item[] items;
    private Filter[] filters;
    private final int totalCount;
}
