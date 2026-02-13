package org.oscar.gym.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oscar.gym.dtos.microservice.StatisticDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class StatisticsProducer {

    private final KafkaTemplate<String, StatisticDto> kafkaTemplate;

    @Value("${spring.kafka.topic.statistics:statistics}")
    private String statisticsTopic;

    public void sendTrainingRecord(StatisticDto dto) {
        kafkaTemplate.send(statisticsTopic, dto.getTrainerUsername(), dto)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Error enviando StatisticDto a Kafka: {}", ex.getMessage());
                    } else {
                        log.info("StatisticDto enviado al topic {} - partition: {}, offset: {}",
                                statisticsTopic, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
                    }
                });
    }
}
