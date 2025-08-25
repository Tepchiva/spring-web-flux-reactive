package com.learning.webfluxreactive.todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table(name = "task")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    @Id
    private Long id;

    private String title;
    private String description;
    private TaskStatusEnum status;
    private TaskPriorityEnum priority;
    private LocalDate dueDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
