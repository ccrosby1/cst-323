/*
 * Contact.java
 * 
 * This class represents a contact entity
 * 
 * Cody Crosby
 * CST-323
 * 2025
 */
package com.example.phonebook.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contacts")
public class Contact {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name", nullable = false, length = 50)
    @NotBlank(message = "Please provide a first name")
    @Size(max = 50, message = "First name must be 50 characters or less")
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    @NotBlank(message = "Please provide a last name")
    @Size(max = 50, message = "Last name must be 50 characters or less")
    private String lastName;

    @Column(name = "phone_number", nullable = false, length = 20)
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    @NotBlank(message = "Please provide an email")
    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must be 100 characters or less")
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // -------- Getters and Setters -------- //
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}
