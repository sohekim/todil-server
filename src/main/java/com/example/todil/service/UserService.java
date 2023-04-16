package com.example.todil.service;

import com.example.todil.domain.dto.UserDto;
import com.example.todil.domain.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findUserById(Long id);

    void incrementStreak(Long id);

    User save(UserDto dto);

}
