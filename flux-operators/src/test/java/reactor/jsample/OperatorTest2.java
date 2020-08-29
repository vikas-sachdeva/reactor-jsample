package reactor.jsample;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public class OperatorTest2 {

    @Test
    void testZipEager() {
        Flux.zip(Flux.just(method1()), Flux.just(method2()))
                .doOnSubscribe(v -> System.out.println("Eager doOnSubscribe callback with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .doOnNext(v -> System.out.println("Eager doOnNext callback with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .doOnComplete(() -> System.out.println("Eager doOnComplete callback with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .subscribe();
    }

    @Test
    void testZipEagerWithException() {
        Flux.zip(Flux.just(method1()), Flux.just(method2()), Flux.just(method3()))
                .doOnSubscribe(v -> System.out.println("Eager doOnSubscribe callback with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .doOnNext(v -> System.out.println("Eager doOnNext callback with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .doOnComplete(() -> System.out.println("Eager doOnComplete callback with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .subscribe();
    }

    @Test
    void testZipLazy() {
        Flux.zip(Flux.defer(() -> Flux.just(method1())), Flux.defer(() -> Flux.just(method2())))
                .doOnSubscribe(v -> System.out.println("Eager doOnSubscribe callback with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .doOnNext(v -> System.out.println("Eager doOnNext callback with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .doOnComplete(() -> System.out.println("Eager doOnComplete callback with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .subscribe();
    }

    @Test
    void testZipLazyWithException() {
        Flux.zip(Flux.defer(() -> Flux.just(method1())), Flux.defer(() -> Flux.just(method2())), Flux.defer(() -> Flux.just(method3())))
                .doOnSubscribe(v -> System.out.println("Eager doOnSubscribe callback with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .doOnNext(v -> System.out.println("Eager doOnNext callback with thread name "
                        + Thread.currentThread().getName() + " and datetime " + LocalDateTime.now()))
                .doOnComplete(() -> System.out.println("Eager doOnComplete callback with thread name "
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
