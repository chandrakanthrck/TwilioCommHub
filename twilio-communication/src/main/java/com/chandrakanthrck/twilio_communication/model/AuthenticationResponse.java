package com.chandrakanthrck.twilio_communication.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Model representing the authentication response containing the JWT token.")
public class AuthenticationResponse {

    @ApiModelProperty(value = "JWT token for the authenticated user", required = true)
    private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
