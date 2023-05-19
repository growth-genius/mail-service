package com.sgyj.mailservice.infra.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("kafka.user-topic")
public class KafkaUserTopicProperties {

    private String authenticationMailTopic;
    private String accountTopic;

}
