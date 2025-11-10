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

@SpringBootApplication
public class WorkoutTrackerApplication {

	/**
	 * Main method to run the Spring Boot application
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(WorkoutTrackerApplication.class, args);
	}

}
