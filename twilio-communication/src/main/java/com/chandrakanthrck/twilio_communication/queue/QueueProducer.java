package com.chandrakanthrck.twilio_communication.queue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class QueueProducer {

    private final RabbitTemplate rabbitTemplate;

    public QueueProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToQueue(String to, String messageContent) {
        String message = String.format("{\"to\": \"%s\", \"content\": \"%s\"}", to, messageContent);
        rabbitTemplate.convertAndSend("smsQueue", message);
    }

}
