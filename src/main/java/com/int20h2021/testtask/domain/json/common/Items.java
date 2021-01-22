package com.int20h2021.testtask.domain.json.common;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class Items implements Serializable {
    private final Item[] items;
    private Filter[] filters;
}
