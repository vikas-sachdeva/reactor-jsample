package reactor.jsample;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

public class FluxCustomSubscriberTest {

    @Test
    void testSubscribeWithConsumer() {

        Flux.range(10, 25)
                .log()
                .subscribe(new BaseSubscriber<Integer>() {

                    int elementsToRequest = 3;
                    int currentElementCount = 0;

                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        System.out.println("hookOnSubscribe method called.");
                        request(elementsToRequest);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        System.out.println("hookOnNext method called on value " + value);
                        currentElementCount++;
                        if (currentElementCount == elementsToRequest) {
                            currentElementCount = 0;
                            request(elementsToRequest);
                        }
                    }
                });
    }
}
