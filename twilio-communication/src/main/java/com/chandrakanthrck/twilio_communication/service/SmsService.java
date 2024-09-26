package com.chandrakanthrck.twilio_communication.service;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private final MeterRegistry meterRegistry;

    public SmsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void sendSms(String to, String messageContent) {
        // Your logic to send SMS

        // Increment a custom counter
        meterRegistry.counter("sms_sent_count").increment();
    }
}
