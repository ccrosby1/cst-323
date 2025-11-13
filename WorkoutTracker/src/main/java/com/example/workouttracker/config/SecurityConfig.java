/**
 * SecurityConfig.java
 * 
 * Configuration class for Spring Security
 * 
 * Cody Crosby & Daniel Hanson
 * CST-323: Cloud Computing
 * GCU 2025
 */
package com.example.workouttracker.config;

import com.example.workouttracker.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/**
	 * Configure security filter chain
	 * @param http HttpSecurity
	 * @return SecurityFilterChain
	 * @throws Exception if an error occurs
	 */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
            	// allow public access to login, registration, and static resources
                .requestMatchers("/login", "/register", "/css/**", "/js/**").permitAll()
                // require authentication of all other requests
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") // custom login page
                .failureUrl("/login?error") // redirect to login on failure
                .defaultSuccessUrl("/home", true)// redirect to home on login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")// redirect to login on logout
                .permitAll()
            );

        return http.build();
    }

    /**
     * Configure authentication manager
     * @param http HttpSecurity
     * @param userService UserService
     * @param passwordEncoder PasswordEncoder
     * @return AuthenticationManager
     * @throws Exception if an error occurs
     */
    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            UserService userService,
            PasswordEncoder passwordEncoder) throws Exception {

        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(userService)
            .passwordEncoder(passwordEncoder)
            .and()
            .build();
    }
    
    /**
     * Password encoder bean
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // Plain text, not for production!
    }
}