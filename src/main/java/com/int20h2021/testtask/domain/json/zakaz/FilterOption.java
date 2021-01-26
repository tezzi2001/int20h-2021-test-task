package com.int20h2021.testtask.domain.json.zakaz;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FilterOption implements Serializable {
    @JsonProperty("query")
    private String id;
    private String name;
    private int count;
}
