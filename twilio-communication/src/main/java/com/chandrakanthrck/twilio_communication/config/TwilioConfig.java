package com.chandrakanthrck.twilio_communication.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;

@Configuration
@Getter // Lombok's annotation to generate getters for all fields
@Slf4j // Lombok's annotation for logging
public class TwilioConfig {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String phoneNumber;

    @PostConstruct
    public void initializeTwilio() {
        try {
            Twilio.init(accountSid, authToken);
            log.info("Twilio initialized successfully with Account SID: {}", accountSid);
        } catch (Exception e) {
            log.error("Failed to initialize Twilio: {}", e.getMessage());
        }
    }
}
