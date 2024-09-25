package com.chandrakanthrck.twilio_communication.service;

import com.chandrakanthrck.twilio_communication.config.TwilioConfig;
import com.chandrakanthrck.twilio_communication.model.MessageLog;
import com.chandrakanthrck.twilio_communication.repository.MessageLogRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import io.micrometer.core.annotation.Timed;  // Add this import
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TwilioSmsService {

    private static final Logger logger = LoggerFactory.getLogger(TwilioSmsService.class);

    private final TwilioConfig twilioConfig;
    private final MessageLogRepository messageLogRepository;

    @Autowired
    public TwilioSmsService(TwilioConfig twilioConfig, MessageLogRepository messageLogRepository) {
        this.twilioConfig = twilioConfig;
        this.messageLogRepository = messageLogRepository;
    }

    @Timed(value = "sms.send.time", description = "Time taken to send an SMS")  // Use @Timed for measuring execution time
    public String sendSms(String to, String messageContent) {
        try {
            logger.info("Sending SMS to: {}", to);

            // Send SMS via Twilio
            Message message = Message.creator(
                    new PhoneNumber(to),  // To number
                    new PhoneNumber(twilioConfig.getPhoneNumber()),  // From Twilio number
                    messageContent  // Message body
            ).create();

            // Save message log to the database
            MessageLog messageLog = new MessageLog(
                    to,
                    twilioConfig.getPhoneNumber(),
                    messageContent,
                    "SENT",
                    LocalDateTime.now()
            );
            messageLogRepository.save(messageLog);

            logger.info("Message sent successfully with SID: {}", message.getSid());
            return "Message sent with SID: " + message.getSid();
        } catch (Exception e) {
            // In case of failure, log the failed message
            MessageLog messageLog = new MessageLog(
                    to,
                    twilioConfig.getPhoneNumber(),
                    messageContent,
                    "FAILED",
                    LocalDateTime.now()
            );
            messageLogRepository.save(messageLog);

            logger.error("Failed to send message to {}: {}", to, e.getMessage());
            return "Failed to send message: " + e.getMessage();
        }
    }
}
