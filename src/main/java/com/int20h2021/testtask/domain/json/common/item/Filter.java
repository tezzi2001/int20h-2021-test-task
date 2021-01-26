package com.int20h2021.testtask.domain.json.common.item;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Filter implements Serializable {
    private String id;
    private String name;
    private FilterOption[] options;
}
