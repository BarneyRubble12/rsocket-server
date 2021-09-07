package com.bedrock.rsocketserver.controller;

import com.bedrock.rsocketserver.client.health.dto.ClientHealthState;
import com.bedrock.rsocketserver.dto.GreetingRequest;
import com.bedrock.rsocketserver.dto.GreetingResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
public class GreetingController {

  @MessageMapping("greetings")
  Flux<GreetingResponse> greet(RSocketRequester clientRsocketConnection, GreetingRequest request) {

    var in = clientRsocketConnection
        .route("health")
        .retrieveFlux(ClientHealthState.class)
        .filter(chs -> !chs.isHealthy());

    var out =  Flux
        .fromStream(Stream.generate(() -> new GreetingResponse(String.format(" Hello %s @ %s !!!!", request.getName(), Instant.now()))))
        .take(100)
        .delayElements(Duration.ofSeconds(1));

    return out.takeUntilOther(in);
  }

}
