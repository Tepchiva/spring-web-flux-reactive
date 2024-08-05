package com.learning.webfluxreactive.service;

import com.learning.webfluxreactive.dto.user.UserRequest;
import com.learning.webfluxreactive.dto.user.UserResponse;
import com.learning.webfluxreactive.entity.User;
import com.learning.webfluxreactive.mapper.UserMapper;
import com.learning.webfluxreactive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Flux<UserResponse> retrieveUsers() {
        return userRepository.findAll().map(UserMapper.INSTANCE::toUserResponse).delayElements(Duration.ofSeconds(1)).log();
    }

    public Mono<UserResponse> createUser(UserRequest userRequest) {
        User user = UserMapper.INSTANCE.toUserModelCreation(userRequest);
        return userRepository.save(user).map(UserMapper.INSTANCE::toUserResponse).log();
    }
}
