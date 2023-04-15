package com.example.todil.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "user")
@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String first_name;

    @Column(length = 200, nullable = false)
    private String last_name;

    @Column(length = 200, nullable = false)
    private String email;

    private Integer current_streak;

    private Integer longest_streak;

    private LocalDateTime last_created;
}
