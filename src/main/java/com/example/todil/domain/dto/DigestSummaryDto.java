package com.example.todil.domain.dto;

import lombok.Builder;

public class DigestSummaryDto {
    public int longest_streak;
    public int current_streak;
    public int total_blocks;

    @Builder
    public DigestSummaryDto (int longest_streak, int current_streak, int total_blocks) {
        this.longest_streak = longest_streak;
        this.current_streak = current_streak;
        this.total_blocks = total_blocks;
    }
}
