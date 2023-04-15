package com.example.todil.apicontroller;

import com.example.todil.domain.dto.InsightSummaryDTO;
import com.example.todil.domain.entity.User;
import com.example.todil.service.BlockService;
import com.example.todil.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/digests")
public class DigestController {

    final UserService userService;
    final BlockService blockService;

    @GetMapping("/summary")
    public ResponseEntity<InsightSummaryDTO> findBlocks(
            @RequestHeader(name = "user_id") Long user_id
    ) {

        Optional<User> optionalUser = userService.findUserById(user_id);
        // todo: if user id is invalid - probably sth better
        if (optionalUser.isEmpty()) return new ResponseEntity("user_not_found",HttpStatus.NOT_FOUND);

        User user = optionalUser.get();
        InsightSummaryDTO insightSummaryDTO = InsightSummaryDTO.builder()
                .longest_streak(user.getLongest_streak())
                .current_streak(user.getCurrent_streak())
                .total_blocks(blockService.getBlockCountByUserId(user_id))
                .build();

        return new ResponseEntity<>(insightSummaryDTO, HttpStatus.OK);
    }

}
