package com.example.todil.service;

import com.example.todil.domain.dto.BlockDto;
import com.example.todil.domain.entity.Block;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Optional;

public interface BlockService {

    Page<Block> findBlocks (Integer page, Integer size);

    Integer getBlockCount();

    Integer getBlockCountByUserId(Long userId);

    Optional<Block> findBlockById(Long id);

    Block save(BlockDto dto) throws Exception;

    @Transactional
    void addTagToBlock(Long blockId, Long tagId);

    Block findClosestBlockByDateAndUserId(Long userId, LocalDate date);
}
