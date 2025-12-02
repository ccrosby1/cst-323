package com.example.phonebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class PhonebookApplication {

	// Logger instance
    private static final Logger logger = LoggerFactory.getLogger(PhonebookApplication.class);
	
    /**
     * Main method to run the Spring Boot application
     * @param args command line arguments
     */
	public static void main(String[] args) {
        logger.info("Starting PhonebookApplication...");
		SpringApplication.run(PhonebookApplication.class, args);
        logger.info("PhonebookApplication started successfully");
	}

}
