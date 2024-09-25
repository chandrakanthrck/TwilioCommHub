package com.chandrakanthrck.twilio_communication.observer;

import com.chandrakanthrck.twilio_communication.model.MessageLog;
import com.chandrakanthrck.twilio_communication.repository.MessageLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DatabaseObserver implements MessageObserver {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseObserver.class);

    private final MessageLogRepository messageLogRepository;

    public DatabaseObserver(MessageLogRepository messageLogRepository) {
        this.messageLogRepository = messageLogRepository;
    }

    @Async
    @Override
    public void update(String message, String status) {
        logger.info("Updating message status in the database. Status: {}", status);

        MessageLog log = MessageLog.builder()
                .toPhoneNumber("recipient") // Example, replace with actual data as needed
                .fromPhoneNumber("sender") // Example, replace with actual data as needed
                .messageContent(message)
                .status(status)
                .sentAt(LocalDateTime.now())
                .build();

        messageLogRepository.save(log);
        logger.info("Message log saved successfully with status: {}", status);
    }
}
