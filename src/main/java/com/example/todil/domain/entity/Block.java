package com.example.todil.domain.entity;

import com.example.todil.domain.dto.BlockDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
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
    private LocalDateTime updateDate;

    // The ON DELETE CASCADE option ensures that if a block or tag is deleted,
    // all related records in the block_tags table will be deleted as well.
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "block_tags",
            joinColumns = @JoinColumn(name = "block_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    protected Block(BlockDto blockDto) {
        this.text = blockDto.getText();
        this.updateDate = blockDto.getUpdateDate();
    }

    public static Block toEntity(BlockDto blockDto) {
        return new Block(blockDto);
    }


}