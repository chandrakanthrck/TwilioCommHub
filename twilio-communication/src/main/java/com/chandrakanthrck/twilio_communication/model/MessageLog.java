package com.chandrakanthrck.twilio_communication.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "message_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "Represents a log entry for SMS messages sent via Twilio.")
public class MessageLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Unique identifier for the message log", required = true)
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty(value = "Recipient's phone number", required = true)
    private String toPhoneNumber;

    @Column(nullable = false)
    @ApiModelProperty(value = "Sender's phone number", required = true)
    private String fromPhoneNumber;

    @Column(nullable = false, length = 500)
    @ApiModelProperty(value = "Content of the SMS message", required = true)
    private String messageContent;

    @Column(nullable = false)
    @ApiModelProperty(value = "Current status of the message", required = true)
    private String status;

    @Column(nullable = false)
    @ApiModelProperty(value = "Timestamp when the message was sent", required = true)
    private LocalDateTime sentAt;

    @Column(nullable = true)
    @ApiModelProperty(value = "Twilio Message SID for tracking the message", required = false)
    private String messageSid;

    @Column(nullable = true)
    @ApiModelProperty(value = "Scheduled time for sending the message", required = false)
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
