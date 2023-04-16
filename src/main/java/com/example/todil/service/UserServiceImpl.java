package com.example.todil.service;

import com.example.todil.domain.dto.UserDto;
import com.example.todil.domain.entity.User;
import com.example.todil.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void incrementStreak(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) return;

        User user = optionalUser.get();

        int newCurrentStreak = user.getCurrent_streak() + 1;
        user.setCurrent_streak(newCurrentStreak);

        if (newCurrentStreak > user.getLongest_streak()) {
            user.setLongest_streak(newCurrentStreak);
        }
    }

    @Override
    public User save(UserDto dto) {
        User user = User.builder()
                .first_name(dto.getFirst_name())
                .last_name(dto.getLast_name())
                .email(dto.getEmail())
                .current_streak(dto.getCurrent_streak())
                .longest_streak(dto.getLongest_streak())
                .last_created(null)
                .build();
        return userRepository.save(user);
    }
}
