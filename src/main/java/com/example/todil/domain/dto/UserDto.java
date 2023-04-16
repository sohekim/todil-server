package com.example.todil.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String first_name;
    private String last_name;
    private String email;
    private Integer current_streak;
    private Integer longest_streak;
    private LocalDateTime last_created;
}
