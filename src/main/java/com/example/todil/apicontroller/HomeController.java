package com.example.todil.apicontroller;

import com.example.todil.domain.dto.HomeDto;
import com.example.todil.domain.entity.Block;
import com.example.todil.domain.entity.User;
import com.example.todil.service.BlockService;
import com.example.todil.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/home")
public class HomeController {

    final UserService userService;
    final BlockService blockService;

    @GetMapping
    public ResponseEntity<HomeDto> findHome(
            @RequestHeader(name = "user_id") Long userId,
            @RequestParam(required = false, defaultValue = "12") Integer monthDiff
    ) {
        // todo: refactoring for using optional
        Optional<User> optionalUser = userService.findUserById(userId);
        // todo: if user id is invalid - probably sth better
        if (optionalUser.isEmpty()) return new ResponseEntity("user_not_found", HttpStatus.NOT_FOUND);

        LocalDate recap_date = LocalDate.now().minusMonths(monthDiff);
        Block recapBlock = blockService.findClosestBlockByDateAndUserId(userId, recap_date);

        User user = optionalUser.get();

        // refresh current streak
        LocalDateTime lastCreatedDateTime = user.getLast_created();
        if (lastCreatedDateTime.toLocalDate().isBefore(LocalDateTime.now().toLocalDate())) {
            user.setCurrent_streak(0);
        }

        HomeDto homeDto = HomeDto.builder()
            .block(recapBlock)
            .longest_streak(user.getLongest_streak())
            .current_streak(user.getCurrent_streak())
            .total_blocks(blockService.getBlockCountByUserId(userId))
            .build();

        return new ResponseEntity<>(homeDto, HttpStatus.OK);
    }
}
