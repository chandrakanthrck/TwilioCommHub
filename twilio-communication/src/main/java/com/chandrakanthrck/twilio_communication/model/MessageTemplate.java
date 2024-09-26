package com.chandrakanthrck.twilio_communication.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "Represents a message template used for sending SMS messages.")
public class MessageTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Unique identifier for the message template", required = true)
    private Long id;

    @Column(nullable = false, unique = true)
    @ApiModelProperty(value = "Name of the message template", required = true)
    private String name;

    @Column(nullable = false, length = 500)
    @ApiModelProperty(value = "Content of the message template", required = true)
    private String templateContent;  // Example: "Hello, {name}! Your order #{orderNumber} is ready."

    // New constructor to allow instantiation with just name and templateContent
    public MessageTemplate(String name, String templateContent) {
        this.name = name;
        this.templateContent = templateContent;
    }
}
