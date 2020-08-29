package reactor.jsample;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public class ZipOperatorTest1 {

    @Test
    void testZipEager() {
        Mono.zip(Mono.just(method1()), Mono.just(method2()))
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
        Mono.zip(Mono.just(method1()), Mono.just(method3()), Mono.just(method2()))
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
    void testZipDelayErrorEager() {
        Mono.zipDelayError(Mono.just(method1()), Mono.just(method3()), Mono.just(method2()))
                .doOnSubscribe(v -> System.out.println("Eager doOnSubscribe callback with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .doOnSuccess(v -> System.out.println("Eager doOnSuccess callback with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .subscribe();
    }

    @Test
    void testZipLazy() {
        Mono.zip(Mono.defer(() -> Mono.just(method1())), Mono.defer(() -> Mono.just(method2())))
                .doOnSubscribe(v -> System.out.println("Lazy doOnSubscribe callback with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .doOnSuccess(v -> System.out.println("Lazy doOnSuccess callback with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .subscribe();
    }

    /**
     * method2() will not be called in below test.
     */
    @Test
    void testZipLazyWithException() {
        Mono.zip(Mono.defer(() -> Mono.just(method1())), Mono.defer(() -> Mono.just(method3())), Mono.defer(() -> Mono.just(method2())))
                .doOnSubscribe(v -> System.out.println("Lazy doOnSubscribe callback with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .doOnSuccess(v -> System.out.println("Lazy doOnSuccess callback with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .subscribe();
    }

    /**
     * method2() will be called in below test.
     */
    @Test
    void testZipDelayErrorLazy() {
        Mono.zipDelayError(Mono.defer(() -> Mono.just(method1())), Mono.defer(() -> Mono.just(method3())), Mono.defer(() -> Mono.just(method2())))
                .doOnSubscribe(v -> System.out.println("Lazy doOnSubscribe callback with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .doOnSuccess(v -> System.out.println("Lazy doOnSuccess callback with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .subscribe();
    }

    private String method1() {
        System.out.println("this is method 1 with thread name " + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now());
        try {
            Thread.sleep(1000 * 5);
            System.out.println("this is method 1 with thread name " + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "one";
    }

    private String method2() {
        System.out.println("this is method 2 with thread name " + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now());
        try {
            Thread.sleep(1000 * 5);
            System.out.println("this is method 2 with thread name " + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "two";
    }

    private String method3() {
        System.out.println("this is method 3 with thread name " + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now());
        try {
            Thread.sleep(1000 * 5);
            System.out.println("this is method 3 with thread name " + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now());
            throw new RuntimeException("Method3 threw exception");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "three";
    }
}