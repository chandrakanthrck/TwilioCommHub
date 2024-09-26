package com.chandrakanthrck.twilio_communication.controller;

import com.chandrakanthrck.twilio_communication.service.TwilioSmsService;
import jakarta.validation.constraints.NotBlank;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sms")
@Validated  // Enable validation
@Api(value = "SMS Management", tags = {"SMS"})
public class SendSmsController {

    private static final Logger logger = LoggerFactory.getLogger(SendSmsController.class);

    private final TwilioSmsService twilioSmsService;

    @Autowired
    public SendSmsController(TwilioSmsService twilioSmsService) {
        this.twilioSmsService = twilioSmsService;
    }

    @PostMapping("/send")
    @ApiOperation(value = "Send an SMS", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SMS sent successfully"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 500, message = "Error sending SMS")
    })
    public ResponseEntity<String> sendSms(
            @RequestParam @NotBlank(message = "Recipient number is required") String to,
            @RequestParam @NotBlank(message = "Message content is required") String message) {

        try {
            String response = twilioSmsService.sendSms(to, message);
            logger.info("SMS sent successfully to: {}", to);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Failed to send SMS to {}: {}", to, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error sending SMS: " + e.getMessage());
        }
    }
}
