package com.int20h2021.testtask.domain.json.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Data
@Entity
@IdClass(ItemPK.class)
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Serializable {
    @Id
    private long id;
    private String title;
    private String href;
    private String img;
    private float price;
    @Id
    private String store;
}
