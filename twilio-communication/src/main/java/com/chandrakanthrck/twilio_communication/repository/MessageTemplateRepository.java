package com.chandrakanthrck.twilio_communication.repository;

import com.chandrakanthrck.twilio_communication.model.MessageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, Long> {

    // Find a template by its name
    MessageTemplate findByName(String name);
}
