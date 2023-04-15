package com.example.todil.service;

import com.example.todil.domain.converter.BlockConverter;
import com.example.todil.domain.dto.BlockDto;
import com.example.todil.domain.entity.Block;
import com.example.todil.domain.entity.Tag;
import com.example.todil.repository.BlockRepository;
import com.example.todil.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlockServiceImpl implements BlockService{

    final BlockRepository blockRepository;
    final TagRepository tagRepository;

    @Override
    public Page<Block> findBlocks(Integer page, Integer size) {
        return blockRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Integer getBlockCount() {
        return blockRepository.countByIdGreaterThanEqual(1L);
    }

    @Override
    public Optional<Block> findBlockById(Long id) {
        return blockRepository.findById(id);
    }

    @Override
    public Block save(BlockDto dto) {
        dto.setUpdateDate(LocalDateTime.now());
        return blockRepository.save(BlockConverter.toEntity(dto));
    }

    @Transactional
    @Override
    public void addTagToBlock(Long blockId, Long tagId) {
        Optional<Block> block = blockRepository.findById(blockId);
        Optional<Tag> tag = tagRepository.findById(tagId);

        if (block.isPresent() && tag.isPresent()) {
            block.get().getTags().add(tag.get());
            tag.get().getBlocks().add(block.get());
        }
    }
}
