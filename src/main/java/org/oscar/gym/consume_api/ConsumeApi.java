package org.oscar.gym.consume_api;

import lombok.extern.slf4j.Slf4j;
import org.oscar.gym.dtos.microservice.StatisticDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ConsumeApi {

    private final WebClient.Builder webClientBuilder;

    @Value("${service.api.name}")
    private String serviceName;

    public ConsumeApi(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public void sendTrainingRecord(StatisticDto dto, String jwt) {
        try {
            ClientResponse response = webClientBuilder.build()
                    .post()
                    .uri("http://" + serviceName + "/statistics")
                    .header("Authorization", "Bearer " + jwt)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(dto)
                    .exchangeToMono(clientResponse -> Mono.just(clientResponse))
                    .block();

            int statusCode = response.statusCode().value();
            String body = response.bodyToMono(String.class).block();

            log.info("Response from {} - Status: {}", serviceName, statusCode);
        } catch (Exception e) {
            log.error("Error calling {} /statistics: {}", serviceName, e.getMessage(), e);
        }
    }
}

