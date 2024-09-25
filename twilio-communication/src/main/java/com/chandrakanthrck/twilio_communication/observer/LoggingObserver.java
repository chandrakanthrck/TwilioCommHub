package com.chandrakanthrck.twilio_communication.observer;

public class LoggingObserver implements MessageObserver {
    @Override
    public void update(String message, String status) {
        System.out.println("Logging message: " + message + " Status: " + status);
    }
}

