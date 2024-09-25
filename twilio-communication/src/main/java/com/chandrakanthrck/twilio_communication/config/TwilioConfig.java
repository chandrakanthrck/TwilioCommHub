package com.chandrakanthrck.twilio_communication.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.twilio.Twilio;

@Configuration
@Getter // Lombok annotation to generate getters for all fields
public class TwilioConfig {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String phoneNumber;

    // Initializes Twilio with SID and Auth Token
    public void initializeTwilio() {
        Twilio.init(accountSid, authToken);
    }
}
