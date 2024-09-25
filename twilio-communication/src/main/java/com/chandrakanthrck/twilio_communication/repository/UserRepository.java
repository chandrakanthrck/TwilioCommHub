package com.chandrakanthrck.twilio_communication.repository;

import com.chandrakanthrck.twilio_communication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // Method to find a user by their username
    User findByUsername(String username);
}
