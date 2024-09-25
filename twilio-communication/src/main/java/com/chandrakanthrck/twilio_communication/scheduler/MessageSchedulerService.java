package com.chandrakanthrck.twilio_communication.scheduler;

import com.chandrakanthrck.twilio_communication.model.MessageLog;
import com.chandrakanthrck.twilio_communication.repository.MessageLogRepository;
import com.chandrakanthrck.twilio_communication.service.TwilioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(MessageSchedulerService.class);

    private final MessageLogRepository messageLogRepository;
    private final TwilioService twilioService;

    public MessageSchedulerService(MessageLogRepository messageLogRepository, TwilioService twilioService) {
        this.messageLogRepository = messageLogRepository;
        this.twilioService = twilioService;
    }

    // Runs every minute to check for scheduled messages
    @Scheduled(fixedRate = 60000)
    public void processScheduledMessages() {
        logger.info("Checking for scheduled messages to process...");

        List<MessageLog> scheduledMessages = messageLogRepository.findByScheduledTimeBeforeAndStatus(
                LocalDateTime.now(), "SCHEDULED");

        if (scheduledMessages.isEmpty()) {
            logger.info("No scheduled messages found.");
            return;
        }

        for (MessageLog messageLog : scheduledMessages) {
            try {
                logger.info("Sending scheduled message to: {}", messageLog.getToPhoneNumber());
                twilioService.sendSms(messageLog.getToPhoneNumber(), messageLog.getMessageContent());
                messageLog.setStatus("SENT");
                messageLogRepository.save(messageLog);
                logger.info("Message sent successfully to: {}", messageLog.getToPhoneNumber());
            } catch (Exception e) {
                logger.error("Failed to send scheduled message to {}: {}", messageLog.getToPhoneNumber(), e.getMessage());
                // Optionally update message status to "FAILED" if needed
            }
        }
    }
}
