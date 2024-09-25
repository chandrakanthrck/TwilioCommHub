package com.chandrakanthrck.twilio_communication.controller;

import com.chandrakanthrck.twilio_communication.model.MessageTemplate;
import com.chandrakanthrck.twilio_communication.queue.QueueProducer;
import com.chandrakanthrck.twilio_communication.repository.MessageTemplateRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/api/template")
public class MessageTemplateController {

    private static final Logger logger = LoggerFactory.getLogger(MessageTemplateController.class);

    private final MessageTemplateRepository messageTemplateRepository;
    private final QueueProducer queueProducer;

    public MessageTemplateController(MessageTemplateRepository messageTemplateRepository, QueueProducer queueProducer) {
        this.messageTemplateRepository = messageTemplateRepository;
        this.queueProducer = queueProducer;
    }

    // Create a new message template
    @PostMapping("/create")
    public ResponseEntity<String> createTemplate(@RequestParam @Valid String name,
                                                 @RequestParam @Valid String templateContent) {
        try {
            MessageTemplate template = MessageTemplate.builder()
                    .name(name)
                    .templateContent(templateContent)
                    .build();
            messageTemplateRepository.save(template);
            logger.info("Template created successfully: {}", name);
            return ResponseEntity.ok("Template created!");
        } catch (Exception e) {
            logger.error("Failed to create template: {}. Error: {}", name, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating template: " + e.getMessage());
        }
    }

    // Send an SMS using a template and replace placeholders with actual values
    @PostMapping("/send")
    public ResponseEntity<String> sendSmsUsingTemplate(@RequestParam @Valid String templateName,
                                                       @RequestParam @Valid String to,
                                                       @RequestParam Map<String, String> params) {
        try {
            MessageTemplate template = messageTemplateRepository.findByName(templateName);
            if (template == null) {
                logger.warn("Template not found: {}", templateName);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Template not found!");
            }

            // Replace placeholders with actual values
            String messageContent = template.getTemplateContent();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                messageContent = messageContent.replace("{" + entry.getKey() + "}", entry.getValue());
            }

            // Send the message using the QueueProducer
            queueProducer.sendToQueue(to, messageContent);
            logger.info("Message sent using template: {} to {}", templateName, to);
            return ResponseEntity.ok("Message sent using template!");
        } catch (Exception e) {
            logger.error("Failed to send message using template: {}. Error: {}", templateName, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error sending message: " + e.getMessage());
        }
    }
}
