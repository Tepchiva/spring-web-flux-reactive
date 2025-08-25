package com.learning.webfluxreactive.todo;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateTaskRequest(
        @NotBlank @Size(max = 255, message = "Title must be less than 255 characters") String title,
        @Size(max = 1000, message = "Description must be less than 1000 characters") String description,
        @FutureOrPresent(message = "Due date must be today or in the future") LocalDate dueDate,
        TaskPriorityEnum priority) {
}
