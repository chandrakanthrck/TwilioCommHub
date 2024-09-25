package com.chandrakanthrck.twilio_communication;

import com.chandrakanthrck.twilio_communication.service.TwilioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TwilioCommunicationApplicationTests {

	@Autowired
	private TwilioService twilioService;

	@Test
	void contextLoads() {
		assertNotNull(twilioService);
	}

	// Add more tests for individual components
}
