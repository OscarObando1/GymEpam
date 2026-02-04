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

    @CircuitBreaker(name = "statisticsService", fallbackMethod = "sendTrainingRecordFallback")
    public void sendTrainingRecord(StatisticDto dto, String jwt) {
        ClientResponse response = webClientBuilder.build()
                .post()
                .uri("http://" + serviceName + "/statistics")
                .header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchangeToMono(clientResponse -> Mono.just(clientResponse))
                .block();

        int statusCode = response.statusCode().value();
        if (response.statusCode().is5xxServerError()) {
            throw new RuntimeException("Server unavaliable");
        }
        String body = response.bodyToMono(String.class).block();

        log.info("Response from {} - Status: {}", serviceName, statusCode);
    }

    public void sendTrainingRecordFallback(StatisticDto dto, String jwt, Throwable t) {
        log.info("The statistics service is currently unavailable");
        fallbackList.add(dto);
    }

}

