package com.int20h2021.testtask.domain.json.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibm.icu.text.Transliterator;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Store implements Deliverable, Serializable {
    private static final Transliterator TRANSLITERATOR = Transliterator.getInstance("Russian-Latin/BGN");

    @Id
    private String id;
    private String name;
    @OneToMany(mappedBy = "store")
    @JsonIgnore
    private Set<Item> items;

    public Store(String name) {
        this.id = TRANSLITERATOR.transliterate(name.replaceAll(" ", "_"));
        this.name = name;
    }

    public void setId(String id) {
        this.id = TRANSLITERATOR.transliterate(id);
    }
}
