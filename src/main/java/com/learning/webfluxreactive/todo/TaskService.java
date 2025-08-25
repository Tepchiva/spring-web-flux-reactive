package com.learning.webfluxreactive.todo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Mono<TaskResponse> createTask(CreateTaskRequest request) {
        return taskRepository.save(Task.builder()
                        .title(request.title())
                        .description(request.description())
                        .status(TaskStatusEnum.PENDING)
                        .dueDate(request.dueDate())
                        .priority(request.priority())
                        .build()
                )
                .map(TaskMapper.INSTANCED::from);
    }
}
