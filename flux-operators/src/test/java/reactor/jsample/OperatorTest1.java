package reactor.jsample;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class OperatorTest1 {

    @Test
    void testMap() {
        Flux.range(2, 10)
                .map(i -> i * i)
                .subscribe(v -> System.out.println("value received after map operation - " + v));
    }

    @Test
    void testMapDifferentType() {
        Flux.range(2, 10)
                .map(i -> "this is different " + i)
                .subscribe(v -> System.out.println("value received after map operation - " + v));
    }

    @Test
    void testFlatMap() {
        Flux.range(2, 10)
                .flatMap(i -> Mono.just(i * i))
                .subscribe(v -> System.out.println("value received after flat map operation " + v));
    }

    @Test
    void testFlatMapWithFlux() {
        Flux.range(2, 10)
                .flatMap(i -> Flux.range(i * i, 2))
                .subscribe(v -> System.out.println("value received after flat map operation " + v));
    }

    @Test
    void testConcat() throws InterruptedException {

        Flux<Integer> first = Flux.range(0, 5)
                .delayElements(Duration.ofMillis(100));

        Flux<Integer> second = Flux.range(5, 5)
                .delayElements(Duration.ofMillis(100));

        Flux.concat(first, second)
                .subscribe(v -> System.out.println("value received after concat operation " + v));

        Thread.sleep(10000);
    }

    /*
     * Order of elements emitted from Flux is not maintained in merge() method.
     */
    @Test
    void testMerge() throws InterruptedException {

        Flux<Integer> first = Flux.range(0, 5)
                .delayElements(Duration.ofMillis(100));

        Flux<Integer> second = Flux.range(5, 5)
                .delayElements(Duration.ofMillis(100));

        Flux.merge(first, second)
                .subscribe(v -> System.out.println("value received after merge operation " + v));

        Thread.sleep(10000);
    }

    @Test
    void testZipWith() throws InterruptedException {

        Flux<Integer> first = Flux.range(0, 5)
                .delayElements(Duration.ofMillis(100));

        Flux<Integer> second = Flux.range(5, 5)
                .delayElements(Duration.ofMillis(100));

        first.zipWith(second)
                .subscribe(v -> System.out.println("value received after zipWith operation on Flux " + v));

        Thread.sleep(10000);
    }

    @Test
    void testZipWithFlux() throws InterruptedException {

        Flux<Integer> first = Flux.range(0, 5)
                .delayElements(Duration.ofMillis(100));

        Flux<Integer> second = Flux.range(5, 5)
                .delayElements(Duration.ofMillis(100));

        Flux.zip(first, second)
                .subscribe(v -> System.out.println("value received after zip operation on Flux " + v));

        Thread.sleep(10000);
    }

    @Test
    void testZipWithCustomCombinator() throws InterruptedException {

        Flux<Integer> first = Flux.range(0, 5)
                .delayElements(Duration.ofMillis(100));

        Flux<Integer> second = Flux.range(5, 5)
                .delayElements(Duration.ofMillis(100));

        Flux.zip(first, second, (item1, item2) -> item1 + " , " + item2)
                .subscribe(v -> System.out.println("value received after zip operation " + v));

        Thread.sleep(10000);
    }
}
