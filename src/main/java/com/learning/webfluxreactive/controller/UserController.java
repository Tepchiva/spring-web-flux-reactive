package com.learning.webfluxreactive.controller;

import com.learning.webfluxreactive.dto.user.UserRequest;
import com.learning.webfluxreactive.dto.user.UserResponse;
import com.learning.webfluxreactive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserResponse> retrieveUsers() {
        return userService.retrieveUsers();
    }

    @PostMapping
    public Mono<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }
}
