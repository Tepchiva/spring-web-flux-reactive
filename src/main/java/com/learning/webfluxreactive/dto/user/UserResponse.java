package com.learning.webfluxreactive.dto.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserResponse(
        Long id,
        String username,
        String firstName,
        String lastName,
        String email,
        String phone
) {}
