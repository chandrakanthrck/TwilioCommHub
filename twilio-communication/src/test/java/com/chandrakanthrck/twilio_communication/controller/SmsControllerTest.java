package com.chandrakanthrck.twilio_communication.controller;

import com.chandrakanthrck.twilio_communication.model.MessageLog;
import com.chandrakanthrck.twilio_communication.queue.QueueProducer;
import com.chandrakanthrck.twilio_communication.repository.MessageLogRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SmsControllerTest {

    @Mock
    private QueueProducer queueProducer;

    @Mock
    private MessageLogRepository messageLogRepository;

    @InjectMocks
    private SmsController smsController;

    public SmsControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendBulkSms_Scheduled() {
        List<String> recipients = Arrays.asList("1234567890");
        String message = "Test message";
        LocalDateTime scheduledTime = LocalDateTime.now().plusMinutes(1);

        ResponseEntity<String> response = smsController.sendBulkSms(recipients, message, scheduledTime);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Bulk messages scheduled"));
        verify(messageLogRepository, times(1)).save(any(MessageLog.class));
    }

    @Test
    void testSendBulkSms_Immediate() {
        List<String> recipients = Arrays.asList("1234567890");
        String message = "Test message";

        ResponseEntity<String> response = smsController.sendBulkSms(recipients, message, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Bulk messages enqueued"));
        verify(queueProducer, times(1)).sendToQueue(anyString(), anyString());
    }

    @Test
    void testSendBulkSms_ErrorHandling() {
        List<String> recipients = Arrays.asList("invalid_number");
        String message = "Test message";

        ResponseEntity<String> response = smsController.sendBulkSms(recipients, message, null);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Error sending bulk messages"));
    }
}
