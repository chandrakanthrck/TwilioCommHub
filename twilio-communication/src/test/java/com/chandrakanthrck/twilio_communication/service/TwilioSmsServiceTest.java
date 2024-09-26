package com.chandrakanthrck.twilio_communication.service;

import com.chandrakanthrck.twilio_communication.config.TwilioConfig;
import com.chandrakanthrck.twilio_communication.model.MessageLog;
import com.chandrakanthrck.twilio_communication.repository.MessageLogRepository;
import com.twilio.rest.api.v2010.account.Message;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TwilioSmsServiceTest {

    @Mock
    private TwilioConfig twilioConfig;

    @Mock
    private MessageLogRepository messageLogRepository;

    @InjectMocks
    private TwilioSmsService twilioSmsService;

    public TwilioSmsServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendSms() {
        String to = "1234567890";
        String messageContent = "Test message";

        when(twilioConfig.getPhoneNumber()).thenReturn("Your_Twilio_Number");

        // Mock Twilio API call
        Message message = mock(Message.class);
        when(message.getSid()).thenReturn("mockSid");

        String response = twilioSmsService.sendSms(to, messageContent);

        assertEquals("Message sent with SID: mockSid", response);

        MessageLog messageLog = new MessageLog(to, "Your_Twilio_Number", messageContent, "SENT", LocalDateTime.now());
        verify(messageLogRepository).save(any(MessageLog.class));
    }
}
