package com.int20h2021.testtask.domain.json.common.item.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibm.icu.text.Transliterator;
import com.int20h2021.testtask.domain.entity.Item;
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
public class Producer implements Deliverable, Serializable {
    private static final Transliterator TRANSLITERATOR = Transliterator.getInstance("Russian-Latin/BGN");

    @Id
    private String id;
    private String name;
    @OneToMany(mappedBy = "producer")
    @JsonIgnore
    private Set<Item> items;

    public Producer(String name) {
        this.id = "p_" + TRANSLITERATOR.transliterate(name.replaceAll(" ", "_"));
        this.name = name;
    }

    public void setId(String id) {
        this.id = TRANSLITERATOR.transliterate(id);
    }
}
