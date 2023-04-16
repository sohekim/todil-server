package com.example.todil.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class DigestDto {

    private String firstName;

    private int total_blocks;

    private int weekly_increase;

    private String digest_text;

    private String suggested_tags;

    private String user_top_tags;

    @Builder
    public DigestDto (String firstName, int total_blocks, int weekly_increase, String digest_text, String suggested_tags, String user_top_tags) {
        this.firstName = firstName;
        this.total_blocks = total_blocks;
        this.weekly_increase = weekly_increase;
        this.digest_text = digest_text;
        this.suggested_tags = suggested_tags;
        this.user_top_tags = user_top_tags;
    }

}
