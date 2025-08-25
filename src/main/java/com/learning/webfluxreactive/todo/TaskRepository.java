package com.learning.webfluxreactive.todo;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface TaskRepository extends R2dbcRepository<Task, Long> {

}
