package com.int20h2021.testtask.domain.json.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

import static java.lang.Math.ceil;
import static org.apache.commons.math3.util.Precision.round;

@Data
@Entity
@IdClass(ItemPK.class)
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
    private int weight;
    private String producer;
    private float pricePerKg;

    public Item(long id, String title, String href, String img, float price, String store, int weight, String producer) {
        weight = parseWeight(weight);
        this.id = id;
        this.title = title;
        this.href = href;
        this.img = img;
        this.price = price;
        this.store = store;
        this.weight = weight == 0 ? 1000 : weight;
        this.producer = producer == null ? "Вагова" : producer;
        this.pricePerKg = price / this.weight * 1000;
    }

    public void setWeight(int weight) {
        weight = parseWeight(weight);
        this.weight = weight == 0 ? 1000 : weight;
    }

    public void setProducer(String producer) {
        this.producer = producer == null ? "Вагова" : producer;
    }

    private int parseWeight(int weight) {
        if (weight < 100) {
            return (int) ceil(round(weight, -1));
        } else if (weight < 1000) {
            return (int) ceil(round(weight, -2));
        } else if (weight < 10000) {
            return (int) ceil(round(weight, -3));
        } else {
            return (int) ceil(round(weight, -4));
        }
    }
}
