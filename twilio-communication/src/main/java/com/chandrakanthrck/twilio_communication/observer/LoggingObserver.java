package com.chandrakanthrck.twilio_communication.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingObserver implements MessageObserver {

    private static final Logger logger = LoggerFactory.getLogger(LoggingObserver.class);

    @Override
    public void update(String message, String status) {
        logger.info("Logging message: {} | Status: {}", message, status);
    }
}
