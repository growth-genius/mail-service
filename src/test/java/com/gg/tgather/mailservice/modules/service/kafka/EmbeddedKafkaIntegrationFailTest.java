package com.gg.tgather.mailservice.modules.service.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gg.tgather.commonservice.dto.mail.EmailMessage;
import com.gg.tgather.commonservice.dto.mail.MailSubject;
import com.gg.tgather.commonservice.properties.KafkaUserTopicProperties;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@Slf4j
@DirtiesContext
@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class EmbeddedKafkaIntegrationFailTest {

    @Autowired
    private MailKafkaTestProducer mailKafkaTestProducer;

    @Autowired
    private MailKafkaTestConsumer mailKafkaTestConsumer;


    @Autowired
    private KafkaUserTopicProperties kafkaUserTopicProperties;

    @Test
    @DisplayName("카프카 메일전송 실패 토픽 전송확인")
    void kafka_mail_send_fail_topic() throws JsonProcessingException, InterruptedException {
        EmailMessage emailMessage = EmailMessage.builder().accountId("QJEQRJQEROJGEQ").to("leesg107@naver.com")
            .mailSubject(MailSubject.VALID_AUTHENTICATION_ACCOUNT).message("authcode_").build();
        mailKafkaTestProducer.send(kafkaUserTopicProperties.getMailSendTopic(), emailMessage);
        mailKafkaTestConsumer.getLatch().await(1000, TimeUnit.MILLISECONDS);
        assertEquals("leesg107@naver.com", mailKafkaTestConsumer.getEmailMessage().getTo());
    }

}
