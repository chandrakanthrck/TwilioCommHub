package com.chandrakanthrck.twilio_communication.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.chandrakanthrck.twilio_communication.config.TwilioConfig;
import com.twilio.Twilio;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    private final TwilioConfig twilioConfig;

    public TwilioService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }

    @Async
    public void sendSms(String to, String messageContent) {
        try {
            Message message = Message.creator(
                    new PhoneNumber(to),
                    new PhoneNumber(twilioConfig.getPhoneNumber()),
                    messageContent).create();

            // Log successful sending
            System.out.println("Message sent to " + to + " with SID: " + message.getSid());

        } catch (Exception e) {
            // Log failure
            System.err.println("Failed to send SMS to " + to + ": " + e.getMessage());
        }
    }
}
