/*
 * ContactController.jave
 * 
 * UI controller for managing Contacts
 * 
 * Cody Crosby
 * CST-323
 * 2025
 */
package com.example.phonebook.controller;

import com.example.phonebook.model.Contact;
import com.example.phonebook.service.ContactService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService service;
    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);
    
    /**
     * Constructor for ContactController
     * @param service ContactService instance
     */
    public ContactController(ContactService service) {
        this.service = service;
    }
        
    /**
     * Home page mapping
     * @return name of home page view
     */
    @GetMapping("/home")
    public String homePage() {
    	logger.info("Loading home page");
        return "index";
    }

    /**
     * Display all Contacts as cards
     * @param model object to hold attributes for view
     * @return name of Contacts vies
     */
    @GetMapping("/contacts-page")
    public String viewContacts(Model model) {
    	logger.info("Loading contact list for UI view");
        List<Contact> contacts = service.getAllContacts();
        model.addAttribute("contacts", contacts);
        return "contacts";
    }
    
    /**
     * Delete Contact by ID
     * @param id ID of Contact to delete
     * @return redirect back to Contacts view
     */
    @PostMapping("/delete/{id}")
    public String deleteContact(@PathVariable int id) {
    	logger.info("Deleting contact with ID: {}", id);
        service.deleteContact(id);
        return "redirect:/contacts/contacts-page";
    }
    
    /**
     * Show form to create new Contact
     * @param model object to hold attributes
     * @return name of contact form view
     */
    @GetMapping("/create")
    public String showCreateForm(Model model) {
    	logger.info("Loading create contact form");
        model.addAttribute("contact", new Contact());
        return "contact-form";
    }

    /**
     * Show form to edit existing Contact
     * @param id ID of Contact to edit
     * @param model object to hold attributes
     * @return name of contact form view
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
    	logger.info("Loading edit form for contact ID: {}", id);
        Optional<Contact> contactOpt = service.getContactById(id);
        
        if (contactOpt.isPresent()) {
            model.addAttribute("contact", contactOpt.get());
            return "contact-form";
        } else {
            logger.warn("Contact with ID {} not found for editing", id);
            return "redirect:/contacts/contacts-page";
        }
    }

    /**
     * Create Contact from form data
     * @param contact Contact object from form
     * @return redirect back to Contacts view
     */
    @PostMapping("/create")
    public String createContact(@Valid @ModelAttribute Contact contact, BindingResult result, Model model) {
    	// check for existing email
    	if (service.emailExists(contact.getEmail())) {
            result.rejectValue("email", "error.contact", "Email is already associated with another contact");
        }
    	
    	//validate form data
    	if (result.hasErrors()) {
            logger.warn("Validation errors while creating contact: {}", result.getAllErrors());
            model.addAttribute("contact", contact);
            return "contact-form";
        }
        
    	// save new contact
        logger.info("Creating new contact: {}", contact);
        service.saveContact(contact);
        return "redirect:/contacts/contacts-page";
    }

    /**
     * Update Contact from form data
     * @param id ID of Contact to update
     * @param contact Contact object from form
     * @return redirect back to Contacts view
     */
    @PostMapping("/update/{id}")
    public String updateContact(@PathVariable int id, @Valid @ModelAttribute Contact contact, BindingResult result, Model model) {
        contact.setId(id);
        
        // validate form data
        if (result.hasErrors()) {
            logger.warn("Validation errors while updating contact ID {}: {}", id, result.getAllErrors());
            model.addAttribute("contact", contact);
            return "contact-form";
        }
        
        // save updated contact
        logger.info("Updating contact with ID {}: {}", id, contact);
        service.saveContact(contact);
        return "redirect:/contacts/contacts-page";
    }
}