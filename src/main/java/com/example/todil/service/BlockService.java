package com.example.todil.service;

import com.example.todil.domain.dto.BlockDto;
import com.example.todil.domain.entity.Block;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface BlockService {

    Page<Block> findBlocks (Integer page, Integer size);

    Integer getBlockCount();

    Optional<Block> findBlockById(Long id);

    // todo: change to dto
    Block save(BlockDto dto) throws Exception;

    @Transactional
    void addTagToBlock(Long blockId, Long tagId);
}
