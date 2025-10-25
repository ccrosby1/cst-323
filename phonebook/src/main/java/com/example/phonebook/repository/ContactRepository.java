/*
 * ContactRepository.java
 * 
 * Interface for Contact repository
 * Extends JpaRepository for CRUD operation
 * 
 * Cody Crosby
 * CST-323
 * 2025
 */
package com.example.phonebook.repository;

import com.example.phonebook.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
	
}