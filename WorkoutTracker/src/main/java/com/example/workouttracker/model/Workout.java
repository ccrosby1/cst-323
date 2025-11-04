/**
 * Workout.java
 * 
 * This class represents a workout model
 * 
 * Cody Crosby & Daniel Hanson
 * CST-323: Cloud Computing
 * GCU 2025
 */
package com.example.workouttracker.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "workout", schema = "workouttracker")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime date;

    @Column(length = 45)
    private String name;

    private int duration;

    @Column(length = 100)
    private String muscleGroup;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "users_user_id", nullable = false)
    private User user;

    // -------- Getters and Setters -------- //
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getMuscleGroup() {
		return muscleGroup;
	}

	public void setMuscleGroup(String muscleGroup) {
		this.muscleGroup = muscleGroup;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
	    return "Workout{" +
	            "id=" + id +
	            ", date=" + date +
	            ", name='" + name + '\'' +
	            ", duration=" + duration +
	            ", muscleGroup='" + muscleGroup + '\'' +
	            ", notes='" + notes + '\'' +
	            '}';
	}
}