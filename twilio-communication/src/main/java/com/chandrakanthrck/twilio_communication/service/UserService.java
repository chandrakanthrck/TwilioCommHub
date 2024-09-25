package com.chandrakanthrck.twilio_communication.service;

import com.chandrakanthrck.twilio_communication.model.User;
import com.chandrakanthrck.twilio_communication.repository.UserRepository;
import io.micrometer.core.annotation.Timed;  // Import for metrics
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Timed(value = "user.registration.time", description = "Time taken to register a new user") // Add @Timed annotation
    public User registerNewUser(String username, String rawPassword) {
        logger.info("Registering new user with username: {}", username);

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));  // Hash the password before saving

        User savedUser = userRepository.save(user);

        logger.info("User registered successfully with username: {}", savedUser.getUsername());

        return savedUser;
    }
}
