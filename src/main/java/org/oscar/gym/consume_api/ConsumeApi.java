package org.oscar.gym.consume_api;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.oscar.gym.dtos.microservice.StatisticDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class ConsumeApi {
    private final WebClient webClient = WebClient.builder().build();

    public void sendTrainingRecord(StatisticDto dto, String jwt) {
        String url = "http://localhost:8082/save";

        String response = webClient.post()
                .uri(url)
                .header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info("Response: " + response);
    }
}
