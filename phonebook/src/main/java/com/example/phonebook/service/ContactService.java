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

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

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
        return repository.findAll();
    }

    /**
     * Get Contact by ID
     * @param id ID of Contact
     * @return optional Contact object
     */
    public Optional<Contact> getContactById(int id) {
        return repository.findById(id);
    }

    /**
     * Update or save Contact
     * @param contact Contact object to be saved 
     * @return Contact object saved
     */
    public Contact saveContact(Contact contact) {
        return repository.save(contact);
    }

    /**
     * Delete Contact by ID
     * @param id ID of Contact to delete
     */
    public void deleteContact(int id) {
        repository.deleteById(id);
    }
}