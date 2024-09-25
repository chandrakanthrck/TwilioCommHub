package com.chandrakanthrck.twilio_communication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.twilio.Twilio;

@Configuration
public class TwilioConfig {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String phoneNumber;

    // This method initializes Twilio, but does not need to be a Spring bean
    public void initializeTwilio() {
        Twilio.init(accountSid, authToken);
    }

    // Getter for phone number, used in the Twilio service
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
