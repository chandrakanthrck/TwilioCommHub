package com.chandrakanthrck.twilio_communication.service;

import com.chandrakanthrck.twilio_communication.config.TwilioConfig;
import com.chandrakanthrck.twilio_communication.model.MessageLog;
import com.chandrakanthrck.twilio_communication.repository.MessageLogRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import io.micrometer.core.annotation.Timed;  // Import for metrics
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;

@Service
public class TwilioService {

    private static final Logger logger = LoggerFactory.getLogger(TwilioService.class);

    private final TwilioConfig twilioConfig;
    private final MessageLogRepository messageLogRepository;

    public TwilioService(TwilioConfig twilioConfig, MessageLogRepository messageLogRepository) {
        this.twilioConfig = twilioConfig;
        this.messageLogRepository = messageLogRepository;
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }

    @Timed(value = "sms.send.time", description = "Time taken to send an SMS") // Track SMS send time
    public void sendSms(String to, String messageContent) {
        try {
            logger.info("Sending SMS to: {}", to);

            Message message = Message.creator(
                            new PhoneNumber(to),
                            new PhoneNumber(twilioConfig.getPhoneNumber()),
                            messageContent)
                    .setStatusCallback(URI.create("http://yourserver.com/api/webhook/sms-status")) // Webhook URL
                    .create();

            // Save the message log with the Twilio Message SID
            MessageLog messageLog = MessageLog.builder()
                    .toPhoneNumber(to)
                    .fromPhoneNumber(twilioConfig.getPhoneNumber())
                    .messageContent(messageContent)
                    .status("SENT")
                    .messageSid(message.getSid())  // Save the SID
                    .sentAt(LocalDateTime.now())
                    .build();
            messageLogRepository.save(messageLog);

            logger.info("Message sent successfully with SID: {}", message.getSid());
        } catch (Exception e) {
            logger.error("Failed to send SMS to {}: {}", to, e.getMessage());
        }
    }
}
