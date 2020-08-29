package reactor.jsample;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public class EmptyMonoTest {

    @Test
    void testSubscribe() {
        Mono.empty()
                .log()
                .subscribe();
    }

    @Test
    void testSubscribeWithConsumer() {
        Mono.empty()
                .log()
                .subscribe(v -> System.out.println("Consumer callback - will not execute for empty Mono " + v));
    }

    @Test
    void testSubscribeWithRunner() {
        Mono.empty().log().subscribe(v -> System.out.println("Consumer callback - will not execute for empty Mono " + v),
                e -> System.out.println("Error callback - will not execute " + e),
                () -> System.out.println("Complete callback - will execute"));
    }

    @Test
    void testDoOn() {
        Mono.empty().log()
                .doOnSubscribe(s -> System.out.println("Subscribe callback - will execute " + s))
                .doOnRequest(r -> System.out.println("Request callback - will execute " + r))
                .doOnNext(n -> System.out.println("Next callback - will not execute for empty Mono " + n))
                .doOnSuccess(c -> System.out.println("Success callback - will execute " + c))
                .doOnTerminate(() -> System.out.println("Terminate callback - will execute"))
                .subscribe();
    }
}