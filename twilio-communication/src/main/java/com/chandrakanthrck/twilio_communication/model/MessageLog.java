package com.chandrakanthrck.twilio_communication.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "message_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String toPhoneNumber;

    @Column(nullable = false)
    private String fromPhoneNumber;

    @Column(nullable = false, length = 500)
    private String messageContent;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime sentAt;

    @Column(nullable = true)
    private String messageSid;

    @Column(nullable = true)
    private LocalDateTime scheduledTime;

    // Constructor without ID
    public MessageLog(String toPhoneNumber, String fromPhoneNumber, String messageContent, String status, LocalDateTime sentAt) {
        this.toPhoneNumber = toPhoneNumber;
        this.fromPhoneNumber = fromPhoneNumber;
        this.messageContent = messageContent;
        this.status = status;
        this.sentAt = sentAt;
    }
}
