/**
 * UserService.java
 * 
 * Service class for managing user authentication and registration
 * 
 * Cody Crosby & Daniel Hanson
 * CST-323: Cloud Computing
 * GCU 2025
 */
package com.example.workouttracker.service;

import com.example.workouttracker.dto.RegistrationDto;
import com.example.workouttracker.model.User;
import com.example.workouttracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

	// Logger for debugging/monitoring
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    // Inject UserRepository
    @Autowired
    private UserRepository userRepository;

    /**
     * Load user by username for authentication
     * @param username username to lookup
     * @return UserDeatails object for authentication
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Attempting to load user by username: {}", username);

        // lookup user in database
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> {
                logger.warn("User not found: {}", username);
                return new UsernameNotFoundException("User not found");
            });

        logger.info("User found: {} (ID: {})", user.getUsername(), user.getUserId());

        // return UserDetails object
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            Collections.singleton(new SimpleGrantedAuthority("USER"))
        );
    }
    
    /**
     * Check if username is taken
     * @param username username to check
     * @return true if taken, false otherwise
     */
    public boolean isUsernameTaken(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    /**
     * Check if email is taken
     * @param email email to check
     * @return true if taken, false otherwise
     */
    public boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    /**
     * Register new user
     * @param dto registration data
     */
    public void registerNewUser(RegistrationDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // USE ENCODING IN PRODUCTION

        try {
            userRepository.save(user); // save new user
            logger.info("Registered new user: {}", user.getUsername());
        } catch (Exception e) { // handle save errors
            logger.error("Failed to register user '{}': {}", user.getUsername(), e.getMessage());
            throw e;
        }
    }
}