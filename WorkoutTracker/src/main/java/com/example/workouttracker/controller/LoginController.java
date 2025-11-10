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

@Controller
public class LoginController {
	
	/**
	 * Displays login page
	 * @return name of login view
	 */
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
}
