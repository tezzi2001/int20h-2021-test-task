package com.int20h2021.testtask.domain.json.zakaz;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Filter implements Serializable {
    @JsonProperty("type")
    private String id;
    private String name;
    private FilterOption[] options;
}
