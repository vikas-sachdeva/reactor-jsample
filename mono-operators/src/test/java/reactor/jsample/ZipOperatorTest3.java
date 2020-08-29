package reactor.jsample;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public class ZipOperatorTest3 {
    /**
     * Below tests depicts the true way of using zip operator.
     */
    @Test
    void testZipEager() {
        Mono.zip(method1(), method2())
                .doOnSubscribe(v -> System.out.println("Eager doOnSubscribe callback with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .doOnSuccess(v -> System.out.println("Eager doOnSuccess callback with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .subscribe();
    }

    /**
     * method2() will not be called in below test.
     */
    @Test
    void testZipEagerWithException() {
        Mono.zip(method1(), method3(), method2())
                .doOnSubscribe(v -> System.out.println("Eager doOnSubscribe callback with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .doOnSuccess(v -> System.out.println("Eager doOnSuccess callback with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .subscribe();
    }

    /**
     * method2() will be called in below test.
     */
    @Test
    void testZipDelayErrorEager() {
        Mono.zipDelayError(method1(), method3(), method2())
                .doOnSubscribe(v -> System.out.println("Eager doOnSubscribe callback with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .doOnSuccess(v -> System.out.println("Eager doOnSuccess callback with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .subscribe();
    }

    private Mono<String> method1() {
        return Mono.just("one")
                .doOnSubscribe(s -> System.out.println("this is method 1 doOnSubscribe with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .map(v -> {
                    System.out.println("this is method 1 map with thread name "
                            + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now());
                    return v;
                })
                .doOnSuccess(v -> System.out.println("this is method 1 doOnSuccess with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()));
    }

    private Mono<String> method2() {
        return Mono.just("two")
                .doOnSubscribe(s -> System.out.println("this is method 2 doOnSubscribe with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .map(v -> {
                    System.out.println("this is method 2 map with thread name "
                            + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now());
                    return v;
                })
                .doOnSuccess(v -> System.out.println("this is method 2 doOnSuccess with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()));
    }

    private Mono<Object> method3() {
        return Mono.error(new RuntimeException("Method3 threw exception"))
                .doOnSubscribe(s -> System.out.println("this is method 3 doOnSubscribe with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .map(v -> {
                    System.out.println("this is method 3 map with thread name "
                            + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now());
                    return v;
                })
                .doOnSuccess(v -> System.out.println("this is method 3 doOnSuccess with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()));
    }
}