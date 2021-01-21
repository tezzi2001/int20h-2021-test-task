package com.int20h2021.testtask.domain.json.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPK implements Serializable {
    @Id
    private long id;
    @Id
    private String store;
}
