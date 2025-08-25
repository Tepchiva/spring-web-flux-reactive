package com.learning.webfluxreactive.todo;

import com.learning.webfluxreactive.exception.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/v1/task")
    public Mono<ResponseWrapper<TaskResponse>> createTask(@Valid @RequestBody CreateTaskRequest createTaskRequest) {
        return taskService.createTask(createTaskRequest).map(ResponseWrapper::new);
    }

}
