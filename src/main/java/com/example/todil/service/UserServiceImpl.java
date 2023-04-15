package com.example.todil.service;

import com.example.todil.domain.entity.User;
import com.example.todil.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements  UserService{

    final UserRepository userRepository;

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void incrementStreak(Long id) {
        User user = userRepository.findById(id).get();

        int newCurrentStreak = user.getCurrent_streak() + 1;
        user.setCurrent_streak(newCurrentStreak);

        if (newCurrentStreak > user.getLongest_streak()) {
            user.setLongest_streak(newCurrentStreak);
        }
    }
}
