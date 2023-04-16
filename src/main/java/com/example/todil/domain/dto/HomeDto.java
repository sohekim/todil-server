package com.example.todil.domain.dto;

import com.example.todil.domain.entity.Block;
import lombok.Builder;

public class HomeDto {

    public int longest_streak;

    public int current_streak;

    public int total_blocks;

    public Block block;

    @Builder
    public HomeDto (int longest_streak, int current_streak, int total_blocks, Block block) {
        this.longest_streak = longest_streak;
        this.current_streak = current_streak;
        this.total_blocks = total_blocks;
        this.block = block;
    }

}
