package com.chandrakanthrck.twilio_communication.observer;

import com.chandrakanthrck.twilio_communication.model.MessageLog;
import com.chandrakanthrck.twilio_communication.repository.MessageLogRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DatabaseObserver implements MessageObserver {

    private final MessageLogRepository messageLogRepository;

    public DatabaseObserver(MessageLogRepository messageLogRepository) {
        this.messageLogRepository = messageLogRepository;
    }

    @Override
    public void update(String message, String status) {
        MessageLog log = MessageLog.builder()
                .toPhoneNumber("recipient") // Example
                .fromPhoneNumber("sender") // Example
                .messageContent(message)
                .status(status)
                .sentAt(LocalDateTime.now())
                .build();
        messageLogRepository.save(log);
    }
}

