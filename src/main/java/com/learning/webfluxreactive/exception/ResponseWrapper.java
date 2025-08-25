package com.learning.webfluxreactive.exception;

import lombok.Data;

import java.util.UUID;

@Data
public class ResponseWrapper<T> {
    private String code;
    private String message;
    private String traceId;
    private T data;

    public ResponseWrapper(T data) {
        this.code = "SUC-000";
        this.message = "Success";
        this.traceId = UUID.randomUUID().toString();
        this.data = data;
    }
}
