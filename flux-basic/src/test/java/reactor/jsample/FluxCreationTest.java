package reactor.jsample;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.time.Duration;
import java.util.Arrays;
import java.util.function.Consumer;

public class FluxCreationTest {

    @Test
    void testJust() {
        Flux.just("A", "B", "C")
                .log()
                .subscribe();
    }

    @Test
    void testFromIterable() {
        Flux.fromIterable(Arrays.asList("A", "B", "C"))
                .log()
                .subscribe();
    }

    @Test
    void testRange() {
        Flux.range(20, 4)
                .log()
                .subscribe();
    }

    @Test
    void testFromIntervalRange() throws InterruptedException {
        Flux.interval(Duration.ofSeconds(1))
                .log()
                .subscribe();
        Thread.sleep(10000);
    }

    @Test
    void testTake() throws InterruptedException {
        Flux.interval(Duration.ofSeconds(1))
                .log()
                .take(3)
                .subscribe();
        Thread.sleep(10000);
    }

    @Test
    void testLimitRate() {
        Flux.range(4, 20)
                .log()
                .limitRate(3)
                .subscribe();

    }

    @Test
    void testLimitRequest() {
        Flux.range(4, 20)
                .log()
                .limitRequest(3)
                .subscribe();

    }

    @Test
    void testGenerateWithConsumer() {
        /*
         * With this overload of generate function, state has to be maintained outside the method.
         */
        Flux<Integer> flux = Flux.generate(new Consumer<>() {

            int state = 0;

            @Override
            public void accept(SynchronousSink<Integer> synchronousSink) {
                synchronousSink.next(state++);
                if (state == 10) {
                    synchronousSink.complete();
                }
            }
        });
        flux.log().subscribe();
    }

    @Test
    void testGenerateWithCallableAndBiFunction() {
        /*
         * With this overload of generate function, updated state can be returned which will be used as parameter in next call of the method.
         */
        Flux<Integer> flux = Flux.generate(() -> 10, (state, synchronousSink) -> {
            synchronousSink.next(state);
            if (state == 20) {
                synchronousSink.complete();
            }
            return state + 1;
        });
        flux.log().subscribe();
    }

    @Test
    void testGenerateWithCallableAndBiFunctionAndConsumer() {
        /*
         * With this overload of generate function, updated state can be returned which will be used as parameter in next call of the method.
         * Also, some cleanup action can be performed on final value of state.
         */
        Flux<Integer> flux = Flux.generate(() -> 10, (state, synchronousSink) -> {
            synchronousSink.next(state);
            if (state == 20) {
                synchronousSink.complete();
            }
            return state + 1;
        }, stateFinalValue -> System.out.println("New value of state " + stateFinalValue));
        flux.log().subscribe();
    }

    @Test
    void testHandle() {
        /*
         *  Handle can serve as a combination of map and filter.
         */
        Flux<String> flux = Flux.just(1, 4, 6, 3, 9, 8).handle((value, synchronousSink) -> {
                    if (value % 2 == 0) {
                        synchronousSink.next("valid value is " + value);
                    }
                }
        );
        flux.log().subscribe();
    }
}
