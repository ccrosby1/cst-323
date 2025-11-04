package com.example.workouttracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.workouttracker.model.User;
import com.example.workouttracker.model.Workout;
import com.example.workouttracker.repository.UserRepository;
import com.example.workouttracker.repository.WorkoutRepository;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private UserRepository userRepository;

    public void saveWorkout(Workout workout, String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            workout.setUser(optionalUser.get());
            workoutRepository.save(workout);
        } else {
            throw new UsernameNotFoundException("User not found: " + username);
        }
    }
    
    public List<Workout> getWorkoutsByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> workoutRepository.findByUser_UserId(user.getUserId()))
                .orElseGet(List::of);
    }
}