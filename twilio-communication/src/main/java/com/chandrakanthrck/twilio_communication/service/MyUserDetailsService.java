package com.chandrakanthrck.twilio_communication.service;

import com.chandrakanthrck.twilio_communication.model.User;
import com.chandrakanthrck.twilio_communication.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Fetching user details for username: {}", username);

        // Fetch the user from the database
        User user = userRepository.findByUsername(username);

        if (user == null) {
            logger.error("User not found with username: {}", username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        logger.info("User found: {}", username);

        // Return a UserDetails object (username, password, authorities)
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
