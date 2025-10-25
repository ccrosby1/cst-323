/*
 * ContactApiController.java
 * 
 * REST API controller for managing contacts
 * 
 * Cody Crosby
 * CST-323
 * 2025
 */
package com.example.phonebook.controller;

import com.example.phonebook.model.Contact;
import com.example.phonebook.service.ContactService;

//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactApiController {

    private final ContactService service;
    private static final Logger logger = LoggerFactory.getLogger(ContactApiController.class);

    /**
     * Constructor for ContactApiController
     * @param service ContactService instance
     */
    public ContactApiController(ContactService service) {
        this.service = service;
    }
       
    /**
     * List all Contacts
     * @return list of Contact objects
     */
    @GetMapping
    public List<Contact> listContacts() {
    	logger.info("Fetching all contacts via API");
        return service.getAllContacts();
    }

    /**
     * Add a new Contact
     * @param contact Contact object to be added
     * @return added Contact object
     */
    @PostMapping
    public Contact addContact(@RequestBody Contact contact) {
    	logger.info("Adding new contact via API: " + contact.getFirstName() + " " + contact.getLastName());
        return service.saveContact(contact);
    }

    /**
     * Retrieve Contact by ID
     * @param id ID of Contact
     * @return Contact object found
     */
    @GetMapping("/{id}")
    public Contact getContact(@PathVariable int id) {
    	logger.info("Fetching contact by ID via API: " + id);
        return service.getContactById(id).orElse(null);
    }

    /**
     * Update existing Contact
     * @param id Id of Contact to be updated
     * @param contact Contact object with updated details
     * @return updated Contact object
     */
    @PutMapping("/{id}")
    public Contact updateContact(@PathVariable int id, @RequestBody Contact contact) {
    	logger.info("Updating contact via API: " + id);
        contact.setId(id);
        return service.saveContact(contact);
    }

    /**
     * Delete Contact by ID
     * @param id ID of Contact to delete
     */
    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable int id) {
    	logger.info("Deleting contact via API: " + id);
        service.deleteContact(id);
    }
}