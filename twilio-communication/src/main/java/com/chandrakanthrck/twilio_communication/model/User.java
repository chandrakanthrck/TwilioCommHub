package com.chandrakanthrck.twilio_communication.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Represents a user of the system.")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Unique identifier for the user", required = true)
    private Long id;

    @Column(nullable = false, unique = true)
    @ApiModelProperty(value = "Unique username for the user", required = true)
    private String username;

    @Column(nullable = false)
    @ApiModelProperty(value = "Password for the user account (stored in encrypted form)", required = true)
    private String password;  // Password will be stored in encrypted form

    // Add any additional fields if required (e.g., roles, email, etc.)
}
