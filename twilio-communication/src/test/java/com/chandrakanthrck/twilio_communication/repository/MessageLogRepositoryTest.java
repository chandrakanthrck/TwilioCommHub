package com.chandrakanthrck.twilio_communication.repository;

import com.chandrakanthrck.twilio_communication.model.MessageLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MessageLogRepositoryTest {

    @Autowired
    private MessageLogRepository messageLogRepository;

    @Test
    void testFindByMessageSid() {
        MessageLog log = new MessageLog("1234567890", "Your_Twilio_Number", "Test message", "SENT", LocalDateTime.now());
        messageLogRepository.save(log);

        MessageLog foundLog = messageLogRepository.findByMessageSid(log.getMessageSid());
        assertNotNull(foundLog);
        assertEquals(log.getMessageContent(), foundLog.getMessageContent());
    }
}
