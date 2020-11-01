package jsample;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

public class ParallelFluxTest {

    @Test
    void test1() throws InterruptedException {
        Flux<Integer> flux = Flux.range(1, 10);
        ParallelFlux<Integer> parallelFlux = flux.parallel(3).runOn(Schedulers.newParallel("parallel", 10));
        parallelFlux.log().doOnSubscribe(s -> {
            System.out.println("doOnSubscribe called. Thread name " + Thread.currentThread().getName());
        }).doOnRequest(c -> {
            System.out.println("doOnRequest called. Thread name " + Thread.currentThread().getName());
        }).doOnNext(c -> {
            System.out.println("doOnNext start with value - " + c + " . Thread name " + Thread.currentThread().getName());
            doSomethingBlocking();
            System.out.println("doOnNext end with value - " + c + " . Thread name " + Thread.currentThread().getName());
        }).doOnComplete(() -> {
            System.out.println("doOnComplete called. Thread name " + Thread.currentThread().getName());
        }).subscribe();
        Thread.sleep(1000 * 2);
    }

    @Test
    void test2() throws InterruptedException {
        Flux<Integer> flux = Flux.range(1, 10);
        ParallelFlux<Integer> parallelFlux = flux.parallel(3).runOn(Schedulers.elastic());
        parallelFlux.log().doOnSubscribe(s -> {
            System.out.println("doOnSubscribe called. Thread name " + Thread.currentThread().getName());
        }).doOnRequest(c -> {
            System.out.println("doOnRequest called. Thread name " + Thread.currentThread().getName());
        }).doOnNext(c -> {
            System.out.println("doOnNext start with value - " + c + " . Thread name " + Thread.currentThread().getName());
            doSomethingBlocking();
            System.out.println("doOnNext end with value - " + c + " . Thread name " + Thread.currentThread().getName());
        }).doOnComplete(() -> {
            System.out.println("doOnComplete called. Thread name " + Thread.currentThread().getName());
        }).subscribe();
        Thread.sleep(1000 * 20);
    }

    @Test
    void test3() throws InterruptedException {
        Flux<Integer> flux = Flux.range(1, 10);
        ParallelFlux<Integer> parallelFlux = flux.parallel().runOn(Schedulers.elastic());
        parallelFlux.log().doOnSubscribe(s -> {
            System.out.println("doOnSubscribe called. Thread name " + Thread.currentThread().getName());
        }).doOnRequest(c -> {
            System.out.println("doOnRequest called. Thread name " + Thread.currentThread().getName());
        }).doOnNext(c -> {
            System.out.println("doOnNext start with value - " + c + " . Thread name " + Thread.currentThread().getName());
            doSomethingBlocking();
            System.out.println("doOnNext end with value - " + c + " . Thread name " + Thread.currentThread().getName());
        }).doOnComplete(() -> {
            System.out.println("doOnComplete called. Thread name " + Thread.currentThread().getName());
        }).subscribe();
        Thread.sleep(1000 * 20);
    }

    @Test
    void test4() throws InterruptedException {
        Flux<Integer> flux = Flux.range(1, 10);
        flux.flatMap(i -> Mono.defer(() -> {
                         System.out.println("flatMap start with value - " + i + " . Thread name " + Thread.currentThread().getName());
                         doSomethingBlocking();
                         System.out.println("flatMap end with value - " + i + " . Thread name " + Thread.currentThread().getName());
                         return Mono.just(i);
                     }).subscribeOn(Schedulers.elastic()), 4
        ).log().doOnSubscribe(s -> {
            System.out.println("doOnSubscribe called. Thread name " + Thread.currentThread().getName());
        }).doOnRequest(c -> {
            System.out.println("doOnRequest called. Thread name " + Thread.currentThread().getName());
        }).doOnNext(c -> {
            System.out.println("doOnNext start with value - " + c + " . Thread name " + Thread.currentThread().getName());
            doSomethingBlocking();
            System.out.println("doOnNext end with value - " + c + " . Thread name " + Thread.currentThread().getName());
        }).doOnComplete(() -> {
            System.out.println("doOnComplete called. Thread name " + Thread.currentThread().getName());
        }).subscribe();
        Thread.sleep(1000 * 200);
    }

    @Test
    void test5() throws InterruptedException {
        Flux<Integer> flux = Flux.range(1, 10);
        flux.flatMap(i -> Mono.defer(() -> {
                         System.out.println("flatMap start with value - " + i + " . Thread name " + Thread.currentThread().getName());
                         doSomethingBlocking();
                         System.out.println("flatMap end with value - " + i + " . Thread name " + Thread.currentThread().getName());
                         return Mono.just(i);
                     }).subscribeOn(Schedulers.newParallel("para", 2)), 4
        ).log().doOnSubscribe(s -> {
            System.out.println("doOnSubscribe called. Thread name " + Thread.currentThread().getName());
        }).doOnRequest(c -> {
            System.out.println("doOnRequest called. Thread name " + Thread.currentThread().getName());
        }).doOnNext(c -> {
            System.out.println("doOnNext start with value - " + c + " . Thread name " + Thread.currentThread().getName());
            doSomethingBlocking();
            System.out.println("doOnNext end with value - " + c + " . Thread name " + Thread.currentThread().getName());
        }).doOnComplete(() -> {
            System.out.println("doOnComplete called. Thread name " + Thread.currentThread().getName());
        }).subscribe();
        Thread.sleep(1000 * 200);
    }

    private void doSomethingBlocking() {
        try {
            Thread.sleep(1000 * 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}