package com.gg.tgather.mailservice.modules.service.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gg.tgather.commonservice.dto.mail.EmailMessage;
import com.gg.tgather.commonservice.dto.mail.MailSubject;
import com.gg.tgather.commonservice.properties.KafkaUserTopicProperties;
import com.gg.tgather.mailservice.infra.config.KafkaTestConfig;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext
@SpringBootTest
@Import({KafkaTestConfig.class, MailKafkaConsumer.class})
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class EmbeddedKafkaIntegrationFailTest {

    @Autowired
    private MailKafkaTestConsumer consumer;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private KafkaUserTopicProperties kafkaUserTopicProperties;

    @Test
    @DisplayName("카프카 메일전송 실패 토픽 전송확인")
    void kafka_mail_send_fail_topic() throws JsonProcessingException, InterruptedException {
        EmailMessage emailMessage = EmailMessage.builder().accountId("QJEQRJQEROJGEQ").to("leesg107@naver.com")
            .mailSubject(MailSubject.VALID_AUTHENTICATION_ACCOUNT).message("authcode_").build();
        kafkaProducer.send(kafkaUserTopicProperties.getAuthenticationMailTopic(), emailMessage);
        consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
        assertEquals("leesg107@naver.com", consumer.getEmailMessage().getTo());
    }

}
