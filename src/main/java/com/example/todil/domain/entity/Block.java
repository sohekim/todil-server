package com.example.todil.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Table(name = "block")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200, nullable = false)
    private String text;

    // todo: add create date
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // The ON DELETE CASCADE option ensures that if a block or tag is deleted,
    // all related records in the block_tags table will be deleted as well.
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "block_tags",
            joinColumns = @JoinColumn(name = "block_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    @Builder
    public Block(Long id, String text, LocalDateTime updateDate, User user) {
        this.text = text;
        this.updateDate = updateDate;
        this.user = user;
        this.id = id;
    }

}
