package com.chandrakanthrck.twilio_communication.queue;

import com.chandrakanthrck.twilio_communication.service.TwilioService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class QueueConsumer {

    private final TwilioService twilioService;

    public QueueConsumer(TwilioService twilioService) {
        this.twilioService = twilioService;
    }

    @RabbitListener(queues = "smsQueue")
    public void processSms(String message) {
        // Extract the "to" number and message content here
        twilioService.sendSms("recipient_phone", message);
    }
}

