package com.learning.webfluxreactive.todo;

import com.learning.webfluxreactive.exception.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskPublisherService taskPublisherService;

    @PostMapping("/v1/task")
    public Mono<ResponseWrapper<TaskResponse>> createTask(@Valid @RequestBody CreateTaskRequest createTaskRequest) {
        return ResponseWrapper.ok(taskService.createTask(createTaskRequest));
    }

    @GetMapping(value = "/v1/task/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<ResponseWrapper<TaskResponse>>> streamTasks(TaskQueryRequest taskQueryRequest) {
        Flux<TaskResponse> initialTaskResponse = taskService.getAllTasks(taskQueryRequest);
        Flux<TaskResponse> update = taskPublisherService.getTaskStream().map(TaskMapper.INSTANCED::from);

        return ResponseWrapper.ok(Flux.merge(initialTaskResponse, update))
                .map(task -> ServerSentEvent.<ResponseWrapper<TaskResponse>>builder()
                        .event("task-event")
                        .id(String.valueOf(task.getData().getId()))
                        .data(task)
                        .build()
                );
    }
}
