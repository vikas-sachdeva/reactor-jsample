package reactor.jsample;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

public class FluxTest {

    @Test
    void testSubscribeWithConsumer() {
        Flux.just("A", "B", "C")
                .log()
                .subscribe(v -> System.out.println("Consumer callback - will execute " + v));
    }

    @Test
    void testSubscribeUncheckedException() {
        Flux.error(new RuntimeException("test exception"))
                .log()
                .subscribe(v -> System.out.println("Consumer callback - will not execute " + v),
                        e -> System.out.println("Error callback - will execute " + e));
    }

    @Test
    void testSubscribeCheckedException() {
        Flux.error(new Exception("test exception"))
                .log()
                .subscribe(v -> System.out.println("Consumer callback - will not execute " + v),
                        e -> System.out.println("Error callback - will execute " + e));
    }

    /**
     * Runner (third argument of subscribe method) will run only in success case.
     */
    @Test
    void testSubscribeWithRunner() {
        Flux.just("A", "B", "C")
                .log()
                .subscribe(v -> System.out.println("Consumer callback - will execute " + v),
                        e -> System.out.println("Error callback - will not execute " + e),
                        () -> System.out.println("Completion callback - will execute"));
    }

    @Test
    void testSubscribeWithRequest() {
        Flux.range(4, 23)
                .log()
                .subscribe(v -> System.out.println("Consumer callback - will execute " + v),
                        e -> System.out.println("Error callback - will not execute " + e),
                        () -> System.out.println("Completion callback - will not execute"),
                        s -> s.request(3));
    }

    @Test
    void testDoOn() {
        Flux.just("A", "B", "C")
                .log()
                .doOnSubscribe(s -> System.out.println("Subscribe callback - will execute " + s))
                .doOnRequest(r -> System.out.println("Request callback - will execute " + r))
                .doOnNext(n -> System.out.println("Next callback - will execute " + n))
                .doOnTerminate(() -> System.out.println("Terminate callback - will execute"))
                .subscribe();
    }

    @Test
    void testDoOnWithException() {
        Flux.error(new Exception("test exception"))
                .log()
                .doOnSubscribe(s -> System.out.println("Subscribe callback - will execute " + s))
                .doOnRequest(r -> System.out.println("Request callback - will execute " + r))
                .doOnError(e -> System.out.println("Error callback - will execute " + e))
                .doOnNext(n -> System.out.println("Next callback - will not execute " + n))
                .doOnTerminate(() -> System.out.println("Terminate callback - will execute"))
                .subscribe();
    }

    @Test
    void testOnErrorResume1() {
        Flux.error(new Exception("test exception"))
                .log()
                .doOnSubscribe(s -> System.out.println("Subscribe callback - will execute " + s))
                .doOnRequest(r -> System.out.println("Request callback - will execute " + r))
                .doOnError(e -> System.out.println("Error callback - will execute " + e))
                .doOnNext(n -> System.out.println("Next callback - will not execute " + n))
                .doOnTerminate(() -> System.out.println("Terminate callback - will execute"))
                .onErrorResume(e -> {
                    System.out.println("On error resume - will execute " + e);
                    return Flux.just("A", "B", "C");
                })
                .subscribe();
    }

    @Test
    void testOnErrorResume2() {
        Flux.error(new Exception("test exception"))
                .onErrorResume(e -> {
                    System.out.println("On error resume callback - will execute " + e);
                    return Flux.just("A", "B", "C");
                })
                .log()
                .doOnSubscribe(s -> System.out.println("Subscribe callback - will execute " + s))
                .doOnRequest(r -> System.out.println("Request callback - will execute " + r))
                .doOnError(e -> System.out.println("Error callback - will not execute " + e))
                .doOnNext(n -> System.out.println("Next callback - will execute " + n))
                .doOnTerminate(() -> System.out.println("Terminate callback - will execute"))
                .subscribe();
    }

    /*
     * onErrorReturn will not print any stack trace of the exception
     */
    @Test
    void testOnErrorReturn() {
        Flux.error(new Exception("test exception"))
                .onErrorReturn("B")
                .log()
                .doOnSubscribe(s -> System.out.println("Subscribe callback - will execute " + s))
                .doOnRequest(r -> System.out.println("Request callback - will execute " + r))
                .doOnError(e -> System.out.println("Error callback - will not execute " + e))
                .doOnNext(n -> System.out.println("Next callback - will execute " + n))
                .doOnTerminate(() -> System.out.println("Terminate callback - will execute"))
                .subscribe();
    }
}
