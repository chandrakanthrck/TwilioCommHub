package com.chandrakanthrck.twilio_communication.controller;

import com.chandrakanthrck.twilio_communication.queue.QueueProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sms")
public class SmsController {

    private final QueueProducer queueProducer;

    public SmsController(QueueProducer queueProducer) {
        this.queueProducer = queueProducer;
    }

    @PostMapping("/send")
    public String sendSms(@RequestParam String to, @RequestParam String message) {
        // Pass both 'to' and 'message' to the QueueProducer
        queueProducer.sendToQueue(to, message);
        return "Message enqueued for sending!";
    }
}
