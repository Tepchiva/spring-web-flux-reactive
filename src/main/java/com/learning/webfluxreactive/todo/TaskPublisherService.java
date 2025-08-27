package com.learning.webfluxreactive.todo;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class TaskPublisherService {

    private final Sinks.Many<Task> taskSink = Sinks.many().multicast().onBackpressureBuffer(Integer.MAX_VALUE, false);

    public Flux<Task> getTaskStream() {
        return taskSink.asFlux();
    }

    public void publishTask(Task task) {
        Sinks.EmitResult result = taskSink.tryEmitNext(task);
        if (result.isFailure()) {
            switch (result) {
                case FAIL_OVERFLOW:
                    // Handle buffer overflow gracefully
                    System.err.println("Task buffer overflow! Dropping item: " + task);
                    // You might log this, implement a retry mechanism, or apply a different strategy
                    break;
                case FAIL_CANCELLED:
                    // Log that the sink has been cancelled
                    System.err.println("Cannot emit item, sink has been cancelled: " + task);
                    break;
                case FAIL_TERMINATED:
                    // Log that the sink has already terminated
                    System.err.println("Cannot emit item, sink has been terminated: " + task);
                    break;
                default:
                    System.err.println("Failed to emit item with unknown reason: " + task);
            }
        }
    }
}