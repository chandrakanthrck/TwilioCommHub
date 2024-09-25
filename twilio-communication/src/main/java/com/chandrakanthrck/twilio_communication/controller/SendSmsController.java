package com.chandrakanthrck.twilio_communication.controller;

import com.chandrakanthrck.twilio_communication.service.TwilioSmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sms")
public class SendSmsController {

    private final TwilioSmsService twilioSmsService;

    @Autowired
    public SendSmsController(TwilioSmsService twilioSmsService) {
        this.twilioSmsService = twilioSmsService;
    }

    @PostMapping("/send")
    public String sendSms(@RequestParam String to, @RequestParam String message) {
        // Use the service to send the SMS
        return twilioSmsService.sendSms(to, message);
    }
}
