package com.chandrakanthrck.twilio_communication.repository;

import com.chandrakanthrck.twilio_communication.model.MessageLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageLogRepository extends JpaRepository<MessageLog, Long> {
    // You can define custom queries here if needed
}
