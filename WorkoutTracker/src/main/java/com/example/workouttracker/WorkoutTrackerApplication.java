/**
 * Main application class for the Workout Tracker application.
 * 
 * Cody Crosby & Daniel Hanson
 * CST-323: Cloud Computing
 * GCU 2025
 */
package com.example.workouttracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class WorkoutTrackerApplication {

	// Logger for application
	private static final Logger logger = LoggerFactory.getLogger(WorkoutTrackerApplication.class);
	
	/**
	 * Main method to run the Spring Boot application
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		logger.info("Starting WorkoutTrackerApplication...");
		SpringApplication.run(WorkoutTrackerApplication.class, args);
		logger.info("WorkoutTrackerApplication started successfully");
	}

}
