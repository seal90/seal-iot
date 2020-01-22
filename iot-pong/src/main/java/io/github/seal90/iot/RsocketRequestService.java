package io.github.seal90.iot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.rsocket.client.BrokerClient;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RsocketRequestService implements ApplicationListener<PayloadApplicationEvent<RSocketRequester>> {

    private final BrokerClient client;

    private RSocketRequester requester;

    public RsocketRequestService(BrokerClient client){
        this.client = client;
    }

    @Override
    public void onApplicationEvent(PayloadApplicationEvent<RSocketRequester> event) {
        log.info("Starting " + client.getProperties().getRouteId());
        RSocketRequester requester = event.getPayload();
        this.requester = requester;
    }

    public Mono<String> sendMessage(String route, String destServiceName, String data){
        return requester.route(route)
                .metadata(client.forwarding(destServiceName))
                .data(data)
                .retrieveMono(String.class)
                .doOnNext(this::logPongs);
    }

    private void logPongs(String payload) {
        log.info("received " + payload + " " + client.getProperties().getRouteId());
    }

}
