/*
 * ContactService.java
 * 
 * Service layer for handling contact operations
 * 
 * Cody Crosby
 * CST-323
 * 2025
 */
package com.example.phonebook.service;

import com.example.phonebook.model.Contact;
import com.example.phonebook.repository.ContactRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

	// Logger instance
    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);
    // Repository instance
    private final ContactRepository repository;

    /**
     * Constructor for ContactService
     * @param repository ContactRepository instance
     */
    public ContactService(ContactRepository repository) {
        this.repository = repository;
    }

    /**
     * Get all Contacts
     * @return list of Contact objects
     */
    public List<Contact> getAllContacts() {
        logger.debug("Entering getAllContacts()");
        List<Contact> contacts = repository.findAll(); // retrieve all contacts
        logger.debug("Exiting getAllContacts() with {} contacts", contacts.size());
        return contacts; // return list of contacts
    }

    /**
     * Get Contact by ID
     * @param id ID of Contact
     * @return optional Contact object
     */
    public Optional<Contact> getContactById(int id) {
        logger.debug("Entering getContactById() with id={}", id);
        Optional<Contact> contact = repository.findById(id); // retrieve contact by id
        logger.debug("Exiting getContactById() with result={}", contact.orElse(null));
        return contact; // return optional contact
    }

    /**
     * Update or save Contact
     * @param contact Contact object to be saved 
     * @return Contact object saved
     */
    public Contact saveContact(Contact contact) {
    	logger.debug("Entering saveContact() with contact={}", contact);
        Contact saved = repository.save(contact); // save contact
        logger.debug("Exiting saveContact() with saved contact id={}", saved.getId());
        return saved; // return saved contact
    }

    /**
     * Delete Contact by ID
     * @param id ID of Contact to delete
     */
    public void deleteContact(int id) {
    	logger.debug("Entering deleteContact() with id={}", id);
        try {
            repository.deleteById(id);// delete contact by id
            logger.info("Deleted contact with id={}", id);
        } catch (Exception e) { // log exception if fails
            logger.error("Exception while deleting contact id={}", id, e);
            throw e;
        }
        logger.debug("Exiting deleteContact()");
    }
    
    /**
     * Check if email exists
     * @param email email to check
     * @return true if email exists, false otherwise
     */
    public boolean emailExists(String email) {
    	logger.debug("Checking if email exists: {}", email);
        boolean exists = repository.findByEmail(email).isPresent(); // check if email exists
        logger.debug("Email {} exists? {}", email, exists);
        return exists; // return status
    }
}