package com.int20h2021.testtask.domain.json.zakaz;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Image implements Serializable {
    @JsonProperty("s1350x1350")
    private String img;
}
