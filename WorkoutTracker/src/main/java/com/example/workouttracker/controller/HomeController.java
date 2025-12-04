/**
 * HomeContoller.java
 * 
 * This controller handles requests to the home page of the workout tracker application.
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
public class HomeController {
		
	// Logger for HomeController
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
		
	/**
	 * Handles request to the home page
	 * @return name of home view
	 */
	@GetMapping({"/", "/home"})
    public String homePage() {
		logger.info("Home page requested");
        return "home";
    }
}