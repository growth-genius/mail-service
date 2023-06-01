package com.gg.tgather.mailservice.infra.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;

@Slf4j
@EnableKafka
@Configuration
public class KafkaExceptionConfig {

    @Bean
    public KafkaListenerErrorHandler mailErrorHandler() {
        return (m, e) -> {
            log.error("[KafkaErrorHandler] kafkaMessage=[" + m.getPayload() + "], errorMessage=[" + e.getMessage() + "]");
            return m.getPayload();
        };
    }
}
