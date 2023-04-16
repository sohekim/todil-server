package com.example.todil.service;

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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlockServiceImpl implements BlockService{

    final BlockRepository blockRepository;
    final TagRepository tagRepository;
    final UserRepository userRepository;

    final UserService userService;

    @Override
    public Page<Block> findBlocks(Integer page, Integer size) {
        return blockRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Integer getBlockCount() {
        return blockRepository.countByIdGreaterThanEqual(1L);
    }

    @Override
    public Integer getBlockCountByUserId(Long user_id) {
        return blockRepository.countBlocksByUserId(user_id);
    }

    @Override
    public Optional<Block> findBlockById(Long id) {
        return blockRepository.findById(id);
    }

    // todo: later get user_id from header - implement header class
    @Transactional
    @Override
    public Block save(BlockDto dto) throws Exception{

        // todo: differentiate creating new block and editing new block

        Optional<User> user = userRepository.findById(dto.getUser_id());
        if (user.isEmpty()) throw new Exception();

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = localDateTime.toLocalDate();

        // STREAK LOGIC
        // if the new block is after the user's last created date -> meaning new block of the day
        // increment current streak
        // update global streak
        // update user get last created

        // new block of the day
         if (user.get().getLast_created() == null || localDate.isAfter(user.get().getLast_created().toLocalDate())) {
             userService.incrementStreak(dto.getUser_id());
             user.get().setLast_created(localDateTime);
         }

        // dto.setUpdateDate(localDateTime);

        Block block = Block.builder()
                .id(dto.getId())
                .text(dto.getText())
                .updateDate(localDateTime)
                // .updateDate(dto.getUpdateDate())
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

    @Override
    public Block findClosestBlockByDateAndUserId(Long userId, LocalDate date) {
        return blockRepository.findClosestBlockByDateAndUserId(userId, date);
    }
}
