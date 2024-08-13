package nior_near.server.domain.payment.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
public class RenderingController {
    @GetMapping("/payment")
    public Mono<Rendering> payment() {
        return Mono.just(Rendering.view("payment").build());
    }
}
