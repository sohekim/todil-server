package com.example.todil.apicontroller;

import com.example.todil.domain.dto.DigestDto;
import com.example.todil.domain.dto.DigestSummaryDto;
import com.example.todil.domain.entity.User;
import com.example.todil.service.BlockService;
import com.example.todil.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/digests")
public class DigestController {

    final UserService userService;
    final BlockService blockService;

    @GetMapping("/summary")
    public ResponseEntity<DigestSummaryDto> findDigestSummary(
            @RequestHeader(name = "user_id") Long user_id
    ) {

        Optional<User> optionalUser = userService.findUserById(user_id);
        // todo: if user id is invalid - probably sth better
        if (optionalUser.isEmpty()) return new ResponseEntity("user_not_found", HttpStatus.NOT_FOUND);

        User user = optionalUser.get();
        DigestSummaryDto digestSummaryDto = DigestSummaryDto.builder()
                .longest_streak(user.getLongest_streak())
                .current_streak(user.getCurrent_streak())
                .total_blocks(blockService.getBlockCountByUserId(user_id))
                .build();

        return new ResponseEntity<>(digestSummaryDto, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<DigestDto> findDigest(
            @RequestHeader(name = "user_id") Long user_id,
            @RequestHeader(name = "date") String stringDateTime
    ) {

        Optional<User> optionalUser = userService.findUserById(user_id);

        LocalDateTime startDateTime = LocalDateTime.parse(stringDateTime);

        // todo: if user id is invalid - probably sth better
        if (optionalUser.isEmpty()) return new ResponseEntity("user_not_found", HttpStatus.NOT_FOUND);

        User user = optionalUser.get();
        Integer prevWeekCount = blockService.countBlocksByUserIdGivenStartAndEndTime(user_id, startDateTime.minusWeeks(1), startDateTime);
        Integer currentWeekCount = blockService.countBlocksByUserIdGivenStartAndEndTime(user_id, startDateTime, startDateTime.plusWeeks(1));

        // todo: after tags - user mapping
        List<String> top_tags = new ArrayList<>(Arrays.asList("Java", "Python", "Spring Boot"));

        DigestDto digestDto = DigestDto.builder()
                                        .firstName(user.getFirst_name())
                                        .total_blocks(blockService.getBlockCountByUserId(user.getId()))
                                        .weekly_increase(prevWeekCount == 0 ? 100 : (int) ((double) (currentWeekCount  - prevWeekCount) / prevWeekCount * 100))
                                        .digest_text("")
                                        .suggested_tags(new ArrayList<>())
                                        .user_top_tags(top_tags).build();

        return new ResponseEntity<>(digestDto, HttpStatus.OK);
    }

}
