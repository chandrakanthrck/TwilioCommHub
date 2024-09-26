package com.chandrakanthrck.twilio_communication.controller;

import com.chandrakanthrck.twilio_communication.model.AuthenticationRequest;
import com.chandrakanthrck.twilio_communication.model.AuthenticationResponse;
import com.chandrakanthrck.twilio_communication.security.JwtUtil;
import com.chandrakanthrck.twilio_communication.service.MyUserDetailsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthenticationControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private MyUserDetailsService userDetailsService;

    @InjectMocks
    private AuthenticationController authenticationController;

    public AuthenticationControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAuthenticationToken() throws Exception {
        String username = "testUser";
        String password = "testPass";

        AuthenticationRequest request = new AuthenticationRequest(username, password);
        UserDetails userDetails = mock(UserDetails.class);

        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtUtil.generateToken(username)).thenReturn("jwt_token");

        ResponseEntity<?> response = authenticationController.createAuthenticationToken(request);

        assertNotNull(response);
        assertEquals("jwt_token", ((AuthenticationResponse) response.getBody()).getJwt());
        verify(authenticationManager).authenticate(any());
    }
}
