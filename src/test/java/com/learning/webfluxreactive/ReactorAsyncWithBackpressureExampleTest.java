package com.learning.webfluxreactive;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

@Disabled
class ReactorAsyncWithBackpressureExampleTest {

    /*
    LATEST strategy keeps only the latest next value, overwriting any previous value if the downstream can’t keep up because its too slow.
    In below output you can see that publishing of all 999 values seems to have gone fine.
    Subscriber also started asynchronously receiving values. But you can see subscriber directly received 999 after 255.
    This means that after 255 (default buffer of 256), all values were replaced with latest & finally last value of 999 received by subscriber.
     */
    @Test
    void ReactorAsyncWithBackpressureLatestExample() throws InterruptedException {

        Flux<Object> fluxAsyncBackp = Flux.create(emitter -> {

            // Publish 1000 numbers
            for (int i = 0; i < 1000; i++) {
                System.out.println(Thread.currentThread().getName() + " | Publishing = " + i);
                emitter.next(i);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            // When all values or emitted, call complete.
            emitter.complete();

        }, FluxSink.OverflowStrategy.LATEST);

        fluxAsyncBackp
                .subscribeOn(Schedulers.boundedElastic())
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(x -> {
                    // Just to show that publisher & subscriber run on different threads.
                    System.out.println(Thread.currentThread().getName() + " | Processing = " + x);
                })
                .subscribe(i -> {
                    // Process received value.
                    System.out.println(Thread.currentThread().getName() + " | Received = " + i);
                    // 100 mills delay to simulate slow subscriber
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }, e -> {
                    // Process error
                    System.err.println(Thread.currentThread().getName() + " | Error = " + e.getMessage());
                });
        /*
         * Notice above -
         *
         * OverflowStrategy.LATEST - Overwrites values if subscriber can't keep up.
         *
         * subscribeOn & publishOn - Put subscriber & publishers on different threads.
         */

        // Since publisher & subscriber run on different thread than main thread, keep
        // main thread active for 100 seconds.
        Thread.sleep(100000);
        Assertions.assertTrue(true);
    }


    /*
    ERROR strategy throws OverflowException in case the downstream can’t keep up due to slowness.
    Publisher can handle exception & make sure to call error handle so
    that subscriber can do handling on subscriber side for such error scenarios.

    You can see in below output that publishing & subscribing started on different threads.
    Subscriber received values till 255 & then error handler was called due to OverflowException. After that subscriber stopped.
     */
    @Test
    void ReactorAsyncWithBackpressureErrorExample() throws InterruptedException {
        Flux.create(emitter -> {
                            // Publish 1000 numbers
                            for (int i = 0; i < 1000; i++) {
                                System.out.println(Thread.currentThread().getName() + " | Publishing = " + i);
                                // BackpressureStrategy.ERROR will cause MissingBackpressureException when
                                // subscriber can't keep up. So handle exception & call error handler.
                                emitter.next(i);
                            }
                            // When all values or emitted, call complete.
                            emitter.complete();

                        }, FluxSink.OverflowStrategy.ERROR
                )
                .subscribeOn(Schedulers.boundedElastic())
                .publishOn(Schedulers.boundedElastic())
                .subscribe(i -> {
                    // Process received value.
                    System.out.println(Thread.currentThread().getName() + " | Received = " + i);
                }, e -> {
                    // Process error
                    System.err.println(Thread.currentThread().getName() + " | Error = " + e.getClass().getSimpleName() + " " + e.getMessage());
                });
        /*
         * Notice above -
         *
         * OverflowStrategy.ERROR - Throws MissingBackpressureException is subscriber
         * can't keep up.
         *
         * subscribeOn & publishOn - Put subscriber & publishers on different threads.
         */

        // Since publisher & subscriber run on different thread than main thread, keep
        // main thread active for 100 seconds.
        Thread.sleep(100000);
    }
}
