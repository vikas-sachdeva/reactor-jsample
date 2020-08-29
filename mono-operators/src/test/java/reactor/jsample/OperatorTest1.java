package reactor.jsample;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class OperatorTest1 {

    @Test
    void testMap() {
        Mono.just(5)
                .map(i -> i * i)
                .subscribe(v -> System.out.println("value received after map operation - " + v));
    }

    @Test
    void testMapDifferentType() {
        Mono.just(5)
                .map(i -> "this is different " + i)
                .subscribe(v -> System.out.println("value received after map operation - " + v));
    }

    @Test
    void testFlatMap() {
        Mono.just(7)
                .flatMap(i -> Mono.just(i * i))
                .subscribe(v -> System.out.println("value received after flat map operation - " + v));
    }

    /**
     * flatMapMany is used for converting Mono into a Flux
     */
    @Test
    void testFlatMapMany() {
        Mono.just(2)
                .flatMapMany(i -> Flux.range(i, 4))
                .subscribe(v -> System.out.println("value received after flat map many operation " + v));
    }

    @Test
    void testConcat() throws InterruptedException {
        Mono<Integer> first = Mono.just(7)
                .delayElement(Duration.ofMillis(1000));

        Mono<Integer> second = Mono.just(9)
                .delayElement(Duration.ofMillis(100));

        Flux.concat(first, second)
                .subscribe(v -> System.out.println("value received after concat operation " + v));

        Thread.sleep(10000);
    }

    /*
     * Order of elements emitted from Flux is not maintained in merge() method.
     */
    @Test
    void testMerge() throws InterruptedException {

        Mono<Integer> first = Mono.just(7)
                .delayElement(Duration.ofMillis(100));

        Mono<Integer> second = Mono.just(9)
                .delayElement(Duration.ofMillis(100));

        Flux.merge(first, second)
                .subscribe(v -> System.out.println("value received after merge operation " + v));

        Thread.sleep(10000);
    }

    @Test
    void testZipWithFlux() throws InterruptedException {

        Mono<Integer> first = Mono.just(7)
                .delayElement(Duration.ofMillis(100));

        Mono<Integer> second = Mono.just(9)
                .delayElement(Duration.ofMillis(100));

        Flux.zip(first, second)
                .subscribe(v -> System.out.println("value received after zip operation on Flux " + v));

        Thread.sleep(10000);
    }

    @Test
    void testZipWithFluxWithCustomCombinator() throws InterruptedException {

        Mono<Integer> first = Mono.just(7)
                .delayElement(Duration.ofMillis(100));

        Mono<Integer> second = Mono.just(9)
                .delayElement(Duration.ofMillis(100));

        Flux.zip(first, second, (item1, item2) -> item1 + " , " + item2)
                .subscribe(v -> System.out.println("value received after zip operation on Flux " + v));

        Thread.sleep(10000);
    }

    @Test
    void testZipWithMono() {

        Mono<Integer> first = Mono.just(1);

        Mono<String> second = Mono.just("one");

        Mono.zip(first, second)
                .subscribe(v -> System.out.println("value received after zip operation on Mono " + v));
    }

    @Test
    void testZipWithMonoWithCustomCombinator() throws InterruptedException {

        Mono<Integer> first = Mono.just(7)
                .delayElement(Duration.ofMillis(100));

        Mono<Integer> second = Mono.just(9)
                .delayElement(Duration.ofMillis(100));

        Mono.zip(first, second, (item1, item2) -> item1 + " , " + item2)
                .subscribe(v -> System.out.println("value received after zip operation on Mono " + v));

        Thread.sleep(10000);
    }
}
