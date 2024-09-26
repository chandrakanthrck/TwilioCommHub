package com.chandrakanthrck.twilio_communication.integration;

import com.chandrakanthrck.twilio_communication.model.MessageTemplate;
import com.chandrakanthrck.twilio_communication.repository.MessageTemplateRepository;
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
public class MessageTemplateIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MessageTemplateRepository messageTemplateRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        messageTemplateRepository.deleteAll(); // Clear the database before each test
    }

    @Test
    public void testCreateMessageTemplate() throws Exception {
        MessageTemplate template = new MessageTemplate();
        template.setName("Greeting");
        template.setTemplateContent("Hello, {name}! Welcome!");

        mockMvc.perform(post("/api/template/create")
                        .param("name", template.getName())
                        .param("templateContent", template.getTemplateContent()))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateMessageTemplateWithExistingName() throws Exception {
        MessageTemplate template = new MessageTemplate();
        template.setName("Greeting");
        template.setTemplateContent("Hello, {name}! Welcome!");
        messageTemplateRepository.save(template);

        mockMvc.perform(post("/api/template/create")
                        .param("name", template.getName())
                        .param("templateContent", "Another message"))
                .andExpect(status().isConflict()); // Assuming you return 409 for duplicates
    }
}
