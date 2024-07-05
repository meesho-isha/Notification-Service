package com.example.NotificationService.kafka;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class SmsProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public SmsProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Long message_id, int retryCount) {
        try {
            log.info("Trying to produce message : {}", message_id);

            Message<Long> kafkaMessage = MessageBuilder
                    .withPayload(message_id)
                    .setHeader(KafkaHeaders.TOPIC, "notifications")
                    .setHeader("retryCount", retryCount)
                    .build();

            kafkaTemplate.send(kafkaMessage);
            log.info("Message produced: {}", message_id);
        } catch (Exception e) {
            log.error("Failed to send message", e);
        }
    }
}
