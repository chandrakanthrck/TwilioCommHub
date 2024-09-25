package com.chandrakanthrck.twilio_communication.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class QueueProducer {

    private static final Logger logger = LoggerFactory.getLogger(QueueProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public QueueProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToQueue(String to, String messageContent) {
        String message = String.format("{\"to\": \"%s\", \"content\": \"%s\"}", to, messageContent);
        logger.info("Sending message to queue: {}", message);
        rabbitTemplate.convertAndSend("smsQueue", message);
        logger.info("Message sent to queue successfully: {}", message);
    }
}
