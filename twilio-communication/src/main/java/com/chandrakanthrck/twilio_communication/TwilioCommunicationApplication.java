package com.chandrakanthrck.twilio_communication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TwilioCommunicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwilioCommunicationApplication.class, args);
	}

}
