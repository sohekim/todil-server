package com.example.todil.domain.converter;

import com.example.todil.domain.dto.BlockDto;
import com.example.todil.domain.entity.Block;

import java.util.Collection;
import java.util.List;

public class BlockConverter extends BaseConverter<BlockDto, Block>{

    private static final BlockConverter converter = new BlockConverter();

    public BlockConverter() {
        super(Block::toEntity, BlockDto::toDto);
    }

    public static BlockDto toDto(Block Block) {
        return converter.convertFromEntity(Block);
    }

    public static Block toEntity(BlockDto BlockDto) {
        return converter.convertFromDto(BlockDto);
    }

    public static List<BlockDto> toDtos(Collection<Block> Blocks) {
        return converter.convertFromEntities(Blocks);
    }

    public static List<Block> toEntities(Collection<BlockDto> BlockDtos) {
        return converter.convertFromDtos(BlockDtos);
    }
}
