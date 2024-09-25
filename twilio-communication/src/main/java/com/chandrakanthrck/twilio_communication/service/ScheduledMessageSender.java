package com.chandrakanthrck.twilio_communication.service;

import com.chandrakanthrck.twilio_communication.model.MessageLog;
import com.chandrakanthrck.twilio_communication.repository.MessageLogRepository;
import io.micrometer.core.annotation.Timed;  // Import for metrics
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduledMessageSender {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledMessageSender.class);

    @Autowired
    private MessageLogRepository messageLogRepository;

    @Autowired
    private TwilioSmsService twilioSmsService;

    // Run every minute to check for pending messages
    @Scheduled(fixedRate = 60000)
    @Timed(value = "scheduled.message.sender.time", description = "Time taken to send scheduled messages") // Track execution time
    public void sendScheduledMessages() {
        logger.info("Checking for pending messages to send...");

        List<MessageLog> pendingMessages = messageLogRepository.findByScheduledTimeBeforeAndStatus(LocalDateTime.now(), "PENDING");

        if (pendingMessages.isEmpty()) {
            logger.info("No pending messages found.");
            return;
        }

        for (MessageLog message : pendingMessages) {
            try {
                logger.info("Sending scheduled message to: {}", message.getToPhoneNumber());
                twilioSmsService.sendSms(message.getToPhoneNumber(), message.getMessageContent());
                message.setStatus("SENT");
                messageLogRepository.save(message);  // Update the status
                logger.info("Message sent successfully to: {}", message.getToPhoneNumber());
            } catch (Exception e) {
                logger.error("Failed to send message to {}: {}", message.getToPhoneNumber(), e.getMessage());
                // Optionally, you could update the message status to "FAILED" here if desired
            }
        }
    }
}
