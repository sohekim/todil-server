package com.example.todil.apicontroller;

import com.example.todil.domain.entity.Tag;
import com.example.todil.service.TagService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tags")
public class TagController {

    final TagService tagService;

    @GetMapping
    public ResponseEntity<List<Tag>> findTags() {
        List<Tag> tags = tagService.findTags();
        if (tags.isEmpty()) return new ResponseEntity("tags not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> save(
            HttpServletRequest request,
            @RequestBody Tag requestedTag) {

        // todo: check duplicate?
        // todo: safeguard empty responses
        Tag tag = tagService.save(requestedTag);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create(request.getRequestURI() + "/" + tag.getId()));
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }
}
