package com.example.todil.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "user")
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public User (String first_name, String last_name, String email, Integer current_streak,Integer longest_streak, LocalDateTime last_created) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.current_streak = current_streak;
        this.longest_streak = longest_streak;
        this.last_created = last_created;
    }
}
