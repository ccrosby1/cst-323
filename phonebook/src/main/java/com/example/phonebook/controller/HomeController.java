package com.example.phonebook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class HomeController {

	// logger instance
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
    /**
     * Index page mapping
     * @return index view name
     */
    @GetMapping("/")
    public String index() {
        logger.info("Serving index page");
        return "index";
    }
}
