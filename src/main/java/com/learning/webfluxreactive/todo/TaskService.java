package com.learning.webfluxreactive.todo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final TaskPublisherService taskPublisherService;

    public Mono<TaskResponse> createTask(CreateTaskRequest request) {
        return taskRepository.save(Task.builder()
                        .title(request.title())
                        .description(request.description())
                        .status(TaskStatusEnum.PENDING.name())
                        .priority(request.priority() != null ? request.priority().name() : null)
                        .dueDate(request.dueDate())
                        .build()
                )
                .flatMap(saved -> taskRepository.findById(saved.getId()))
                .doOnNext(taskPublisherService::publishTask)
                .map(TaskMapper.INSTANCED::from);
    }

    public Flux<TaskResponse> getAllTasks(TaskQueryRequest query) {
        Criteria criteria = Criteria.empty();
        if (query.status() != null) {
            criteria = Criteria.where("status").is(query.status().name());
        }

        Sort sort = Sort.by(Sort.Direction.fromString(query.safeDirection()), query.safeSortColumn());

        Pageable pageable = PageRequest.of(query.page(), query.size(), sort);

        return r2dbcEntityTemplate.select(Task.class)
                .matching(Query.query(criteria).with(pageable))
                .all()
                .map(TaskMapper.INSTANCED::from);
    }
}
