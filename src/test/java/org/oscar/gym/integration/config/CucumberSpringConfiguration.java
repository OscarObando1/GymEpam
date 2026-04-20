package org.oscar.gym.integration.config;

import io.cucumber.spring.CucumberContextConfiguration;
import org.oscar.gym.producer.StatisticsProducer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@CucumberContextConfiguration
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.profiles.active=local",
                "KEY_ALGO=cucumber-integration-test-secret-key-32",
                "eureka.client.enabled=false",
                "eureka.client.register-with-eureka=false",
                "eureka.client.fetch-registry=false",
                "spring.cloud.discovery.enabled=false",
                "spring.kafka.bootstrap-servers=localhost:19092",
                "spring.datasource.url=jdbc:h2:mem:gymdb;NON_KEYWORDS=USER"
        }
)
public class CucumberSpringConfiguration {

    // Mock Kafka producer so training creation doesn't need a real Kafka broker
    @MockBean
    private StatisticsProducer statisticsProducer;
}
