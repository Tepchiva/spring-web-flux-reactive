package com.learning.webfluxreactive.mapper;

import com.learning.webfluxreactive.dto.user.UserRequest;
import com.learning.webfluxreactive.dto.user.UserResponse;
import com.learning.webfluxreactive.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse toUserResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", expression = "java(generateUsername(userRequest))")
    User toUserModelCreation(UserRequest userRequest);

    default String generateUsername(UserRequest userRequest) {
        String username = userRequest.firstName() + userRequest.lastName();
        return username.toLowerCase();
    }
}
