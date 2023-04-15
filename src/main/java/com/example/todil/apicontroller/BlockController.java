package com.example.todil.apicontroller;

import com.example.todil.domain.dto.BlockDto;
import com.example.todil.domain.entity.Block;
import com.example.todil.domain.entity.Tag;
import com.example.todil.domain.entity.User;
import com.example.todil.service.BlockService;
import com.example.todil.service.TagService;
import com.example.todil.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blocks")
public class BlockController {

    // question: why interface not impl
    final BlockService blockService;
    final TagService tagService;
    final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Block> findBlockById(@PathVariable(value = "id") Long id) {
        Optional<Block> block = blockService.findBlockById(id);
        if (block.isEmpty()) return new ResponseEntity("block not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(block.get(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Block>> findBlocks(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        Page<Block> blockPage = blockService.findBlocks(page, size);
        if (blockPage.isEmpty()) return new ResponseEntity("block not found", HttpStatus.NOT_FOUND);
        List<Block> blocks = blockPage.getContent();
        return new ResponseEntity<>(blocks, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> save(
            HttpServletRequest request,
//            @RequestHeader("key") String key,
            @RequestBody BlockDto dto) {

        try {

            Optional<User> user = userService.findUserById(dto.getUser_id());
            if (user.isEmpty()) return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);

//        if (!myKey.equals(key)) {
//            return new ResponseEntity<>("permission denied", HttpStatus.FORBIDDEN);
//        }

            // 1. save tags
            List<Tag> tags = dto.getTags().stream()
                    .map(tagName -> tagService.findTagByName(tagName)
                            .orElseGet(() -> tagService.save(new Tag(tagName))))
                    .collect(Collectors.toList());


            // 2. save block
            Block block = blockService.save(dto);

            // 3. Associate tags with block
            tags.forEach(tag -> blockService.addTagToBlock(block.getId(), tag.getId()));


            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(URI.create(request.getRequestURI() + "/" + block.getId()));
            return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);

        } catch (Exception e) {
            // todo: better error code
            return new ResponseEntity<>("some error - has to be fixed", HttpStatus.BAD_REQUEST);
        }

    }

}
