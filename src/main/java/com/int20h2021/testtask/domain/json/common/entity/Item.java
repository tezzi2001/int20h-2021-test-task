package com.int20h2021.testtask.domain.json.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

import static java.lang.Math.ceil;
import static org.apache.commons.math3.util.Precision.round;

@Data
@Entity
@NoArgsConstructor
public class Item implements Serializable {
    @Id
    private long id;
    private String title;
    private String href;
    private String img;
    private float price;
    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonIgnore
    private Store store;
    private int weight;
    @ManyToOne
    @JoinColumn(name = "producer_id")
    @JsonIgnore
    private Producer producer;
    private float pricePerKg;

    @JsonProperty("store")
    private String storeJson;
    @JsonProperty("producer")
    private String producerJson;

    public Item(long id, String title, String href, String img, float price, String store, int weight, String producer) {
        weight = parseWeight(weight);
        this.id = id;
        this.title = title;
        this.href = href;
        this.img = img;
        this.price = price;
        this.store = new Store(store);
        this.weight = weight == 0 ? 1000 : weight;
        this.producer = producer == null ?
                new Producer("Вагова") :
                new Producer(producer);
        this.pricePerKg = price / this.weight * 1000;
    }

    public void setWeight(int weight) {
        weight = parseWeight(weight);
        this.weight = weight == 0 ?
                1000 :
                weight;
    }

    public void setProducer(Producer producer) {
        this.producer = producer.getName() == null ?
                new Producer("Вагова") :
                producer;
    }

    public void setProducer(String producer) {
        this.producer = producer == null ?
                new Producer("Вагова") :
                new Producer(producer);
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

    // should be deleted in future releases
    public void parse() {
        this.storeJson = this.store.getName();
        this.producerJson = this.producer.getName();
    }
}
