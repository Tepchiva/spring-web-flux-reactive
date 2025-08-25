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
                        .status(TaskStatusEnum.PENDING.name())
                        .priority(request.priority() != null ? request.priority().name() : null)
                        .dueDate(request.dueDate())
                        .build()
                )
                .map(TaskMapper.INSTANCED::from);
    }
}
