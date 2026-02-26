package org.oscar.gym.producer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oscar.gym.dtos.microservice.StatisticDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("StatisticsProducer")
class StatisticsProducerTest {

    @Mock
    private KafkaTemplate<String, StatisticDto> kafkaTemplate;

    @InjectMocks
    private StatisticsProducer statisticsProducer;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(statisticsProducer, "statisticsTopic", "statistics");
    }

    @Test
    @DisplayName("sendTrainingRecord invokes KafkaTemplate with correct topic, key and dto")
    void sendTrainingRecord_invokesKafkaTemplateWithCorrectArgs() {
        StatisticDto dto = new StatisticDto();
        dto.setTrainerUsername("trainer.john");
        dto.setTrainingDate(java.time.LocalDate.now());

        SendResult<String, StatisticDto> sendResult = mock(SendResult.class);
        when(sendResult.getRecordMetadata()).thenReturn(mock(org.apache.kafka.clients.producer.RecordMetadata.class));
        when(kafkaTemplate.send(anyString(), anyString(), any(StatisticDto.class)))
                .thenReturn(CompletableFuture.completedFuture(sendResult));

        statisticsProducer.sendTrainingRecord(dto);

        verify(kafkaTemplate).send(eq("statistics"), eq("trainer.john"), eq(dto));
    }

    @Test
    @DisplayName("sendTrainingRecord uses trainerUsername as message key")
    void sendTrainingRecord_usesTrainerUsernameAsKey() {
        StatisticDto dto = new StatisticDto();
        dto.setTrainerUsername("trainer.jane");

        when(kafkaTemplate.send(anyString(), anyString(), any(StatisticDto.class)))
                .thenReturn(new CompletableFuture<>());

        statisticsProducer.sendTrainingRecord(dto);

        verify(kafkaTemplate).send(eq("statistics"), eq("trainer.jane"), eq(dto));
    }
}
