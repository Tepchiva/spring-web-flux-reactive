package com.learning.webfluxreactive.todo;

import org.apache.commons.lang3.StringUtils;

public record TaskQueryRequest(TaskStatusEnum status, String sort, String direction, Integer page, Integer size) {
    public TaskQueryRequest {
        if (StringUtils.isBlank(sort)) sort = "id";
        if (StringUtils.isBlank(direction)) direction = "asc";
        if (page == null || page < 0) page = 0;
        if (size == null || size <= 0) size = 10;
    }

    public String safeSortColumn() {
        return switch (sort) {
            case "id", "title", "status", "priority", "due_date", "created_at", "updated_at" -> sort;
            default -> "id";
        };
    }

    public String safeDirection() {
        return StringUtils.isNoneBlank(direction) && direction.equalsIgnoreCase("desc") ? "DESC" : "ASC";
    }
}
