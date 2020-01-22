package io.github.seal90.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@AllArgsConstructor
public class PingController {

    private RsocketRequestService rsocketRequestService;

    @RequestMapping("/ping")
    public Mono<String> ping(String route, String destServiceName, String data){
        log.info("receive route : {}, destServiceName : {}, data : {}", route, destServiceName, data);
        return rsocketRequestService.sendMessage(route, destServiceName, data);
    }
}
