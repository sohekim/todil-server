package com.example.todil.domain.dto;

import com.example.todil.domain.entity.Block;
import com.example.todil.domain.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class BlockDto {

    private Long id;
    private String text;
    private LocalDateTime updateDate;
    private List<String> tags;

    // todo: fix constructor
    public BlockDto(Block entity
//                    , List<Tag> tagList
    ) {
        this.id = entity.getId();
        this.text = entity.getText();
        this.updateDate = entity.getUpdateDate();
        this.tags = entity.getTags().stream().map(Tag::getTagName).collect(Collectors.toList());
//        this.tags = tagList.stream().map(Tag::getTagName).collect(Collectors.toList());
    }

    public static BlockDto toDto(Block entity) {
        return new BlockDto(entity);
    }



}
