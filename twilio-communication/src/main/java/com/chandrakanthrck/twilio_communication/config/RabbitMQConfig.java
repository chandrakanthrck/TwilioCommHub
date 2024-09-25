package com.chandrakanthrck.twilio_communication.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue smsQueue() {
        return new Queue("smsQueue", false);
    }
}
