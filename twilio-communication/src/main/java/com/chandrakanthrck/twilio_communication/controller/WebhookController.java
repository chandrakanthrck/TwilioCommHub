package com.chandrakanthrck.twilio_communication.controller;

import com.chandrakanthrck.twilio_communication.model.MessageLog;
import com.chandrakanthrck.twilio_communication.repository.MessageLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/webhook")
public class WebhookController {

    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);

    private final MessageLogRepository messageLogRepository;

    public WebhookController(MessageLogRepository messageLogRepository) {
        this.messageLogRepository = messageLogRepository;
    }

    @PostMapping("/sms-status")
    public ResponseEntity<String> updateSmsStatus(@RequestParam Map<String, String> requestParams) {
        String messageSid = requestParams.get("MessageSid");
        String status = requestParams.get("MessageStatus");

        if (messageSid == null || status == null) {
            logger.warn("Received webhook with missing parameters: {}", requestParams);
            return ResponseEntity.badRequest().body("Missing MessageSid or MessageStatus");
        }

        // Find the message in the database by SID and update its status
        MessageLog messageLog = messageLogRepository.findByMessageSid(messageSid);
        if (messageLog != null) {
            messageLog.setStatus(status.toUpperCase());
            messageLogRepository.save(messageLog);
            logger.info("Updated message status: SID = {}, Status = {}", messageSid, status);
            return ResponseEntity.ok("Status updated successfully");
        } else {
            logger.warn("Message not found for SID: {}", messageSid);
            return ResponseEntity.notFound().build();
        }
    }
}
