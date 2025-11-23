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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    // Inject BCryptPasswordEncoder from SecurityConfig
    @Autowired
    private PasswordEncoder passwordEncoder;

    /***
     * Load a user by username for authentication
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.warn("User not found: {}", username);
                    return new UsernameNotFoundException("User not found");
                });

        logger.info("User authenticated: {} (ID: {})", user.getUsername(), user.getUserId());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    /***
	 * Check if username is taken
	 * @param username username to check
	 * @return true if username is taken, false otherwise
	 */
    public boolean isUsernameTaken(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    /***
     * Check if email is taken
     * @param email email to check
     * @return true if email is taken, false otherwise
     */
    public boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    /***
     * Register new user with BCrypt hashing
     * @param dto registration data transfer object
     */
    public void registerNewUser(RegistrationDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());

        // hash password before saving
        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(hashedPassword);

        try {
            userRepository.save(user);
            logger.info("Successfully registered new user: {}", user.getUsername());
        } catch (Exception e) {
            logger.error("Failed to register user '{}': {}", user.getUsername(), e.getMessage());
            throw e;
        }
    }
}
