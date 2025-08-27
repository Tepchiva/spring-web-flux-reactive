package com.learning.webfluxreactive.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public static <T> Mono<ResponseWrapper<T>> ok(Mono<? extends T> monoResponse) {
        return monoResponse.map(ResponseWrapper::new);
    }

    public static <T> Flux<ResponseWrapper<T>> ok(Flux<? extends T> allTasks) {
        return allTasks.map(ResponseWrapper::new);
    }
}
