package com.chandrakanthrck.twilio_communication.service;

import com.chandrakanthrck.twilio_communication.config.TwilioConfig;
import com.chandrakanthrck.twilio_communication.observer.MessageObserver;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TwilioService {

    private List<MessageObserver> observers = new ArrayList<>();

    private final TwilioConfig twilioConfig;

    public TwilioService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
        // Initialize Twilio
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }

    public void addObserver(MessageObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(MessageObserver observer) {
        observers.remove(observer);
    }

    public void sendSms(String to, String messageContent) {
        try {
            Message message = Message.creator(
                    new PhoneNumber(to),
                    new PhoneNumber(twilioConfig.getPhoneNumber()),
                    messageContent).create();

            notifyObservers(messageContent, "SENT");

        } catch (Exception e) {
            notifyObservers(messageContent, "FAILED");
            e.printStackTrace();
        }
    }

    private void notifyObservers(String message, String status) {
        for (MessageObserver observer : observers) {
            observer.update(message, status);
        }
    }
}
