package com.chandrakanthrck.twilio_communication.repository;

import com.chandrakanthrck.twilio_communication.model.MessageLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageLogRepository extends JpaRepository<MessageLog, Long> {

    // Find a message log by its Twilio Message SID
    MessageLog findByMessageSid(String messageSid);

    // Find messages where the scheduled time is before a certain time and the status matches
    List<MessageLog> findByScheduledTimeBeforeAndStatus(LocalDateTime scheduledTime, String status);
}
