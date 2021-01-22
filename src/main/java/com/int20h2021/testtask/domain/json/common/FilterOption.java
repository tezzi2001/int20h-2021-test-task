package com.int20h2021.testtask.domain.json.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class FilterOption implements Serializable {
    private String id;
    private String name;
}
