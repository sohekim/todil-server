package com.example.todil.apicontroller;

import com.example.todil.domain.dto.DigestDto;
import com.example.todil.domain.dto.DigestSummaryDto;
import com.example.todil.domain.entity.Block;
import com.example.todil.domain.entity.User;
import com.example.todil.domain.model.ChatGPTResponse;
import com.example.todil.service.BlockService;
import com.example.todil.service.ChatGPTService;
import com.example.todil.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/digests")
public class DigestController {

    final UserService userService;
    final BlockService blockService;
    final ChatGPTService chatGPTService;

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

        List<Block> weekBlocks = blockService.findAllBlocksByUserIdGivenTimeFrame(user_id, startDateTime, startDateTime.plusWeeks(1));
        Integer currentWeekCount = weekBlocks.size();

        StringBuilder sb = new StringBuilder();
        weekBlocks.forEach(s -> sb.append(s.getText() + ","));

        sb.deleteCharAt(sb.length()-1);

        final String chatGPTPrompt = "You have to do two task.\n" +
                "\n" +
                "First task:  you are an encouraging and fun teacher. Imagine you are writing a  weekly report card that is about five to six sentence to a student who learned what's in the bracket." +
                "  ["+ sb +"] " +
                "Remember to be casual and supportive about learning and progress.  Don't format it as a email.  Only include the actual text. Don't include the student name. Don't include sentence like Don't forget to ask questions if you need help\n" +
                "\n" +
                "Second task: based on what the student learn, recommend the student three relevant and trending frameworks or programming languages that the student have not learn yet. When answering the second task, only include the name and not any explanation or words explaining the output. No intro sentences. Only output the names using commas.\n" +
                "\n" +
                "Format the answers in the following way: first task answer+second task answer\n" +
                "Include the + to divide the two answers but don't include any space or next line.";

        ChatGPTResponse chatGPTResponse = chatGPTService.askQuestion(chatGPTPrompt);

        String[] digestTextArray = chatGPTResponse.getChoices().get(0).getText().split("\\+");

        // todo: after tags - user mapping
        String top_tags = "Java,Python,Spring Boot";

        DigestDto digestDto = DigestDto.builder()
                                        .firstName(user.getFirst_name())
                                        .total_blocks(blockService.getBlockCountByUserId(user.getId()))
                                        .weekly_increase(prevWeekCount == 0 ? 100 : (int) ((double) (currentWeekCount  - prevWeekCount) / prevWeekCount * 100))
                                        .digest_text(digestTextArray[0])
                                        .suggested_tags(digestTextArray[1])
                                        .user_top_tags(top_tags).build();

        return new ResponseEntity<>(digestDto, HttpStatus.OK);
    }

}
