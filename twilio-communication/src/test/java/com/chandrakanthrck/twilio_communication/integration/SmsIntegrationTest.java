package com.chandrakanthrck.twilio_communication.integration;

import com.chandrakanthrck.twilio_communication.queue.QueueProducer;
import com.chandrakanthrck.twilio_communication.repository.MessageLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SmsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MessageLogRepository messageLogRepository;

    @Autowired
    private QueueProducer queueProducer;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        messageLogRepository.deleteAll(); // Clear the database before each test
    }

    @Test
    public void testSendBulkSms() throws Exception {
        String messageContent = "Hello, this is a test message.";
        String[] recipients = {"+1234567890", "+0987654321"};

        mockMvc.perform(post("/api/sms/send/bulk")
                        .param("to", String.join(",", recipients))
                        .param("message", messageContent))
                .andExpect(status().isOk());

        // Verify that the messages are enqueued or saved
        // Add additional assertions as necessary
    }

    @Test
    public void testSendBulkSmsWithInvalidInput() throws Exception {
        String messageContent = "";

        mockMvc.perform(post("/api/sms/send/bulk")
                        .param("to", ""))
                .andExpect(status().isBadRequest());
    }
}
