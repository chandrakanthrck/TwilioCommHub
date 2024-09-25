package com.chandrakanthrck.twilio_communication.controller;

import com.chandrakanthrck.twilio_communication.model.MessageLog;
import com.chandrakanthrck.twilio_communication.queue.QueueProducer;
import com.chandrakanthrck.twilio_communication.repository.MessageLogRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sms")
@Validated  // Enable validation
public class SmsController {

    private static final Logger logger = LoggerFactory.getLogger(SmsController.class);

    private final QueueProducer queueProducer;
    private final MessageLogRepository messageLogRepository;

    public SmsController(QueueProducer queueProducer, MessageLogRepository messageLogRepository) {
        this.queueProducer = queueProducer;
        this.messageLogRepository = messageLogRepository;
    }

    @PostMapping("/send/bulk")
    public ResponseEntity<String> sendBulkSms(
            @RequestParam @NotEmpty(message = "Recipient list cannot be empty") List<String> to,
            @RequestParam @NotBlank(message = "Message content cannot be empty") String message,
            @RequestParam(required = false) LocalDateTime scheduledTime) {

        try {
            if (scheduledTime != null && scheduledTime.isAfter(LocalDateTime.now())) {
                // Handle scheduling for multiple recipients
                for (String recipient : to) {
                    MessageLog messageLog = MessageLog.builder()
                            .toPhoneNumber(recipient)
                            .fromPhoneNumber("Your_Twilio_Number") // Replace with actual Twilio number
                            .messageContent(message)
                            .status("SCHEDULED")
                            .scheduledTime(scheduledTime)
                            .build();
                    messageLogRepository.save(messageLog);
                }
                logger.info("Bulk messages scheduled for {}", scheduledTime);
                return ResponseEntity.ok("Bulk messages scheduled for " + scheduledTime);
            } else {
                // Enqueue multiple messages for immediate sending
                for (String recipient : to) {
                    queueProducer.sendToQueue(recipient, message);
                }
                logger.info("Bulk messages enqueued for sending to: {}", to);
                return ResponseEntity.ok("Bulk messages enqueued for sending!");
            }
        } catch (Exception e) {
            logger.error("Error occurred while sending bulk SMS: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error sending bulk messages: " + e.getMessage());
        }
    }
}
