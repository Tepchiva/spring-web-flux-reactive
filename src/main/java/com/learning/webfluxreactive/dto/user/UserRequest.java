package com.learning.webfluxreactive.dto.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserRequest(
        @NotNull
        String firstName,
        @NotNull
        String lastName,
        @NotNull
        String email,
        String phone,
        @NotNull
        String password
) {}
