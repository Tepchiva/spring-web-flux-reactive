package com.learning.webfluxreactive.repository;

import com.learning.webfluxreactive.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
}
