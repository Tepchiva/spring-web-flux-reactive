package com.learning.webfluxreactive.todo;

import lombok.Data;

@Data
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private String dueDate;
    private String createdAt;
    private String updatedAt;
}
