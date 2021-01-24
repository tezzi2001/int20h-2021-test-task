package com.int20h2021.testtask.domain.json.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class FilterOption implements Serializable {
    public static final String DELIMITER = "_";
    private String id;
    private String name;

    public FilterOption(String id, String name, String type) {
        this.id = id + DELIMITER + type;
        this.name = name;
    }
}
