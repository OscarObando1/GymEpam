package org.oscar.gym.consume_api;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.oscar.gym.dtos.microservice.StatisticDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@Getter
public class ConsumeApi {

    private final WebClient.Builder webClientBuilder;

    @Value("${service.api.name}")
    private String serviceName;

    private final List<StatisticDto> fallbackList = new ArrayList<>();

    public ConsumeApi(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

   

}

