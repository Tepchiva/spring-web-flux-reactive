package com.learning.webfluxreactive.todo;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TaskMapper {

    TaskMapper INSTANCED = Mappers.getMapper(TaskMapper.class);

    TaskResponse from(Task task);
}
