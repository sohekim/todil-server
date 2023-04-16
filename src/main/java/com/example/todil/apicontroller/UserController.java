package com.example.todil.apicontroller;

import com.example.todil.domain.dto.UserDto;
import com.example.todil.domain.entity.User;
import com.example.todil.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    final UserService userService;
    @PostMapping
    public ResponseEntity<String> save(
            HttpServletRequest request,
            @RequestBody UserDto dto) {
        try {
            User user = userService.save(dto);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(URI.create(request.getRequestURI() + "/" + user.getId()));

            return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("some error - has to be fixed", HttpStatus.BAD_REQUEST);
        }
    }

}
