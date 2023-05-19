package com.sgyj.mailservice.modules.service.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sgyj.mailservice.infra.properties.KafkaUserTopicProperties;
import com.sgyj.mailservice.modules.dto.AccountDto;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class EmbeddedKafkaIntegrationTest {

    @Autowired
    private MailKafkaConsumer consumer;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private KafkaUserTopicProperties kafkaUserTopicProperties;

    @Test
    @DisplayName("카프카 컨슈머 동작 확인")
    void kafkaConsumerTest() throws JsonProcessingException, InterruptedException {
        AccountDto accountDto = new AccountDto();
        String email = "leesg107@naver.com";
        accountDto.setEmail(email);
        kafkaProducer.send(kafkaUserTopicProperties.getAuthenticationMailTopic(), accountDto);
        consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
        assertEquals(email, consumer.getEmail());
    }


}
