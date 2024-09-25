package com.chandrakanthrck.twilio_communication.queue;

import com.chandrakanthrck.twilio_communication.service.TwilioService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class QueueConsumer {

    private static final Logger logger = LoggerFactory.getLogger(QueueConsumer.class);

    private final TwilioService twilioService;

    public QueueConsumer(TwilioService twilioService) {
        this.twilioService = twilioService;
    }

    @Async
    @RabbitListener(queues = "smsQueue")
    public void processSms(String message) {
        logger.info("Received message from queue: {}", message);

        try {
            JSONObject json = new JSONObject(message);
            String to = json.getString("to");
            String messageContent = json.getString("content");

            logger.info("Sending SMS to: {}", to);
            twilioService.sendSms(to, messageContent);  // Handle Twilio SMS sending
            logger.info("SMS sent successfully to: {}", to);
        } catch (Exception e) {
            logger.error("Failed to process message: {}. Error: {}", message, e.getMessage());
        }
    }
}
