package com.chandrakanthrck.twilio_communication.controller;

import com.chandrakanthrck.twilio_communication.model.AuthenticationRequest;
import com.chandrakanthrck.twilio_communication.model.AuthenticationResponse;
import com.chandrakanthrck.twilio_communication.security.JwtUtil;
import com.chandrakanthrck.twilio_communication.service.MyUserDetailsService;
import jakarta.validation.Valid;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "Authentication Management", tags = {"Authentication"})
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @PostMapping("/api/authenticate")
    @ApiOperation(value = "Authenticate user and generate JWT", response = AuthenticationResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User authenticated successfully"),
            @ApiResponse(code = 401, message = "Authentication failed"),
            @ApiResponse(code = 400, message = "Invalid request")
    })
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );

            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails.getUsername());

            logger.info("User {} authenticated successfully.", authenticationRequest.getUsername());
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        } catch (Exception ex) {
            logger.error("Authentication failed for user {}: {}", authenticationRequest.getUsername(), ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + ex.getMessage());
        }
    }
}
