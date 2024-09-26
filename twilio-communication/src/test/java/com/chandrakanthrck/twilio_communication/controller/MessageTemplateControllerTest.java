package com.chandrakanthrck.twilio_communication.controller;

import com.chandrakanthrck.twilio_communication.model.MessageTemplate;
import com.chandrakanthrck.twilio_communication.queue.QueueProducer;
import com.chandrakanthrck.twilio_communication.repository.MessageTemplateRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MessageTemplateControllerTest {

    @Mock
    private MessageTemplateRepository messageTemplateRepository;

    @Mock
    private QueueProducer queueProducer;

    @InjectMocks
    private MessageTemplateController messageTemplateController;

    public MessageTemplateControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTemplate() {
        String name = "Test Template";
        String content = "Hello, {name}!";

        ResponseEntity<String> response = messageTemplateController.createTemplate(name, content);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Template created"));
        verify(messageTemplateRepository, times(1)).save(any(MessageTemplate.class));
    }

    @Test
    void testSendSmsUsingTemplate() {
        String templateName = "Test Template";
        String to = "1234567890";
        Map<String, String> params = new HashMap<>();
        params.put("name", "John");

        when(messageTemplateRepository.findByName(templateName)).thenReturn(new MessageTemplate(templateName, "Hello, {name}!"));

        ResponseEntity<String> response = messageTemplateController.sendSmsUsingTemplate(templateName, to, params);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Message sent using template"));
        verify(queueProducer, times(1)).sendToQueue(anyString(), anyString());
    }
}
