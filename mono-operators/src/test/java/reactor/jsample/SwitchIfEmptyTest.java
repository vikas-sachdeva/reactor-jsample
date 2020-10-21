package reactor.jsample;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public class SwitchIfEmptyTest {

    @Test
    void test1() {
        Mono.just("test")
            .filter(s -> s.equals("test"))
            .flatMap(s -> method1())
            .switchIfEmpty(method2())
            .log()
            .subscribe();
    }

    private Mono<String> method1() {
        System.out.println("method-1 will execute");
        return Mono.just("method1");
    }

    private Mono<String> method2() {
        System.out.println("method-2 will execute before subscription");
        return Mono.just("method2");
    }

    @Test
    void test2() {
        Mono.just("test")
            .filter(s -> s.equals("test"))
            .flatMap(s -> method1())
            .switchIfEmpty(Mono.defer(() -> method3()))
            .log()
            .subscribe();
    }

    private Mono<String> method3() {
        System.out.println("method-3 will not execute");
        return Mono.just("method3");
    }

    @Test
    void test3() {
        Mono.just("test1")
            .filter(s -> s.equals("test"))
            .flatMap(s -> method5())
            .switchIfEmpty(Mono.defer(() -> method4()))
            .log()
            .subscribe();
    }

    private Mono<String> method5() {
        System.out.println("method-5 will not execute");
        return Mono.just("method5");
    }

    private Mono<String> method4() {
        System.out.println("method-4 will execute only after subscription");
        return Mono.just("method4");
    }
}
