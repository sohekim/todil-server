package com.example.todil.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;

@Table(name = "tag")
@Entity
@Getter
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200, nullable = false)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private Set<Block> blocks = new HashSet<>();

    public Tag (String name) {
        this.name = name;
    }

    protected Tag() {}

    @JsonProperty("name")
    public String getTagName() {
        return name;
    }
}
