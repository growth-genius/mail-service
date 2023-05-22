package com.sgyj.mailservice.modules.service.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sgyj.commonservice.dto.mail.EmailMessage;
import com.sgyj.commonservice.dto.mail.MailSubject;
import com.sgyj.mailservice.infra.properties.KafkaUserTopicProperties;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext
@SpringBootTest
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
        EmailMessage emailMessage = EmailMessage.builder().accountId("QJEQRJQEROJGEQ").to("leesg107@naver.com")
            .mailSubject(MailSubject.VALID_AUTHENTICATION_ACCOUNT).message("이승구 나쁜놈").build();
        kafkaProducer.send(kafkaUserTopicProperties.getAuthenticationMailTopic(), emailMessage);
        consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
        assertEquals("leesg107@naver.com", emailMessage.getTo());
    }

}

