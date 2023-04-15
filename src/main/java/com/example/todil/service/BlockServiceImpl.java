package com.example.todil.service;

import com.example.todil.domain.converter.BlockConverter;
import com.example.todil.domain.dto.BlockDto;
import com.example.todil.domain.entity.Block;
import com.example.todil.domain.entity.Tag;
import com.example.todil.domain.entity.User;
import com.example.todil.repository.BlockRepository;
import com.example.todil.repository.TagRepository;
import com.example.todil.repository.UserRepository;
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
    final UserRepository userRepository;

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

    // todo: later get user_id from header
    // implement header class
    @Override
    public Block save(BlockDto dto) throws Exception{
        // todo: differntiate creating new block and editing new block

        LocalDateTime now = LocalDateTime.now();
        dto.setUpdateDate(now);



//        if (now.isAfter(userRepository.))

        // find user's last_created
        // if current date is more recent, meaning new entry of the date
        // update last_created in USER

        // call increase streak()
        // userRepository.increaseStreak();

        Optional<User> user = userRepository.findById(dto.getUser_id());
        if (user.isEmpty()) throw new Exception();

        Block block = Block.builder()
                .id(dto.getId())
                .text(dto.getText())
                .updateDate(dto.getUpdateDate())
                .user(user.get()).build();

        return blockRepository.save(block);
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
