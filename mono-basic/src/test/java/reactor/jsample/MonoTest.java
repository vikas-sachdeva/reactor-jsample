package reactor.jsample;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public class MonoTest {

    @Test
    void testSubscribe() {
        Mono.just("A")
                .log()
                .subscribe();
    }

    @Test
    void testSubscribeWithConsumer() {
        Mono.just("A")
                .log()
                .subscribe(v -> System.out.println("Consumer callback - will execute " + v));
    }

    @Test
    void testSubscribeUncheckedException() {
        Mono.error(new RuntimeException("test exception"))
                .log()
                .subscribe(v -> System.out.println("Consumer callback - will not execute " + v),
                        e -> System.out.println("Error callback - will execute " + e));
    }

    @Test
    void testSubscribeCheckedException() {
        Mono.error(new Exception("test exception"))
                .log()
                .subscribe(v -> System.out.println("Consumer callback - will not execute " + v),
                        e -> System.out.println("Error callback - will execute " + e));
    }

    /**
     * Runner (third argument of subscribe method) will run only in success case.
     */
    @Test
    void testSubscribeWithRunner() {
        Mono.just("A").log().subscribe(v -> System.out.println("Consumer callback - will execute " + v),
                e -> System.out.println("Error callback - will not execute " + e),
                () -> System.out.println("Complete callback - will execute"));
    }

    @Test
    void testDoOn() {
        Mono.just("A").log()
                .doOnSubscribe(s -> System.out.println("Subscribe callback - will execute " + s))
                .doOnRequest(r -> System.out.println("Request callback - will execute " + r))
                .doOnNext(n -> System.out.println("Next callback - will execute " + n))
                .doOnSuccess(c -> System.out.println("Success callback - will execute " + c))
                .doOnTerminate(() -> System.out.println("Terminate callback - will execute"))
                .subscribe();
    }

    @Test
    void testFromSupplier() {
        Mono.fromSupplier(() -> "A")
                .log()
                .doOnSubscribe(s -> System.out.println("Subscribe callback - will execute " + s))
                .doOnRequest(r -> System.out.println("Request callback - will execute " + r))
                .doOnNext(n -> System.out.println("Next callback - will execute " + n))
                .doOnSuccess(c -> System.out.println("Success callback - will execute " + c))
                .doOnTerminate(() -> System.out.println("Terminate callback - will execute"))
                .subscribe();
    }

    @Test
    void testDoOnWithException() {
        Mono.error(new Exception("test exception"))
                .log()
                .doOnSubscribe(s -> System.out.println("Subscribe callback - will execute " + s))
                .doOnRequest(r -> System.out.println("Request callback - will execute " + r))
                .doOnError(e -> System.out.println("Error callback executed - will execute " + e))
                .doOnNext(n -> System.out.println("Next callback - will not execute " + n))
                .doOnSuccess(c -> System.out.println("Success callback - will not execute " + c))
                .doOnTerminate(() -> System.out.println("Terminate callback - will execute"))
                .subscribe();
    }

    @Test
    void testOnErrorResume1() {
        Mono.error(new Exception("test exception"))
                .log()
                .doOnSubscribe(s -> System.out.println("Subscribe callback - will execute " + s))
                .doOnRequest(r -> System.out.println("Request callback - will execute " + r))
                .doOnError(e -> System.out.println("Error callback - will execute " + e))
                .doOnNext(n -> System.out.println("Next callback - will not execute " + n))
                .doOnSuccess(c -> System.out.println("Success callback - will not execute " + c))
                .doOnTerminate(() -> System.out.println("Terminate callback - will execute"))
                .onErrorResume(e -> {
                    System.out.println("On error resume callback - will execute " + e);
                    return Mono.just("A");
                })
                .subscribe();
    }

    @Test
    void testOnErrorResume2() {
        Mono.error(new Exception("test exception"))
                .onErrorResume(e -> {
                    System.out.println("On error resume - will execute " + e);
                    return Mono.just("A");
                })
                .log()
                .doOnSubscribe(s -> System.out.println("Subscribe callback - will execute " + s))
                .doOnRequest(r -> System.out.println("Request callback - will execute " + r))
                .doOnError(e -> System.out.println("Error callback - will not execute " + e))
                .doOnNext(n -> System.out.println("Next callback - will execute " + n))
                .doOnSuccess(c -> System.out.println("Success callback - will execute " + c))
                .doOnTerminate(() -> System.out.println("Terminate callback - will execute"))
                .subscribe();
    }

    /*
     * onErrorReturn will not print any stack trace of the exception
     */
    @Test
    void testOnErrorReturn() {
        Mono.error(new Exception("test exception"))
                .onErrorReturn("B")
                .log()
                .doOnSubscribe(s -> System.out.println("Subscribe callback - will execute " + s))
                .doOnRequest(r -> System.out.println("Request callback - will execute " + r))
                .doOnError(e -> System.out.println("Error callback - will not execute " + e))
                .doOnNext(n -> System.out.println("Next callback - will execute " + n))
                .doOnSuccess(c -> System.out.println("Success callback - will execute " + c))
                .doOnTerminate(() -> System.out.println("Terminate callback - will execute"))
                .subscribe();
    }
}
