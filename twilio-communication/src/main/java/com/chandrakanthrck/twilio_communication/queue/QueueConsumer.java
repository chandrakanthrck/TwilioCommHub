package com.chandrakanthrck.twilio_communication.queue;

import com.chandrakanthrck.twilio_communication.service.TwilioService;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class QueueConsumer {

    private final TwilioService twilioService;

    public QueueConsumer(TwilioService twilioService) {
        this.twilioService = twilioService;
    }

    @Async
    @RabbitListener(queues = "smsQueue")
    public void processSms(String message) {
        // Process the message asynchronously
        JSONObject json = new JSONObject(message);
        String to = json.getString("to");
        String messageContent = json.getString("content");

        twilioService.sendSms(to, messageContent);  // Handle Twilio SMS sending
    }
}
