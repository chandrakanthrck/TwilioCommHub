package com.chandrakanthrck.twilio_communication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "message_templates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, length = 500)
    private String templateContent;  // Example: "Hello, {name}! Your order #{orderNumber} is ready."
}
