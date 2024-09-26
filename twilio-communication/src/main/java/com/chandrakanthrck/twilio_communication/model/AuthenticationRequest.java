package com.chandrakanthrck.twilio_communication.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Model representing the authentication request containing username and password.")
public class AuthenticationRequest {

    @ApiModelProperty(value = "Username of the user", required = true)
    private String username;

    @ApiModelProperty(value = "Password of the user", required = true)
    private String password;

    // Default constructor for JSON Parsing
    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
