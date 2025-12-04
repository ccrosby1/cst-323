/**
 * LoginController.java
 * 
 * This controller handles login requests for the workout tracker application
 * 
 * Cody Crosby & Daniel Hanson
 * CST-323: Cloud Computing
 * GCU 2025
 */
package com.example.workouttracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class LoginController {
	
	// Logger for LoginController
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
		
	/**
	 * Displays login page
	 * @return name of login view
	 */
	@GetMapping("/login")
	public String loginPage() {
		logger.info("Login page requested");
		return "login";
	}
}
