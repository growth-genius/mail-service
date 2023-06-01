package com.gg.tgather.mailservice.modules.service.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public MailKafkaTestConsumer mailKafkaTestConsumer() {
        return new MailKafkaTestConsumer();
    }

}
