package io.github.seal90.business;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class PongMessage {

    @MessageMapping("pong")
    public Mono<String> pong(String data){
        log.info("receive data is : {}", data);
        return Mono.just(data);
    }
}
