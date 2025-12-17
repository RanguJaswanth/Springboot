package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Repository.UserRepository;
import com.example.demo.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create user with salary based on designation
    public User createUser(User user) {
        // Set the salary based on designation when creating the user
        setSalaryBasedOnDesignation(user);
        return userRepository.save(user);
    }

    // Get user by ID
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    // Raise salary based on designation (10% for Manager, 5% for Programmer, 3% for Tester)
    public Optional<User> raiseSalaryBasedOnDesignation(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            double percentage = 0;

            // Determine the raise percentage based on the designation
            switch (user.getDesignation().toLowerCase()) {
                case "manager":
                    percentage = 10;  // Manager gets a 10% raise
                    break;
                case "programmer":
                    percentage = 5;   // Programmer gets a 5% raise
                    break;
                case "tester":
                    percentage = 3;   // Tester gets a 3% raise
                    break;
                default:
                    throw new IllegalArgumentException("Unknown designation: " + user.getDesignation());
            }

            // Calculate new salary based on percentage increase
            double newSalary = user.getSalary() * (1 + percentage / 100.0);
            user.setSalary(newSalary);
            
            // Save the updated user object to the repository
            userRepository.save(user);

            return Optional.of(user);  // Return the updated user
        }

        return Optional.empty();  // Return empty if user not found
    }

    // Helper method to set salary based on designation
    private void setSalaryBasedOnDesignation(User user) {
        switch (user.getDesignation().toLowerCase()) {
            case "manager":
                user.setSalary(30000);
                break;
            case "programmer":
                user.setSalary(25000);
                break;
            case "tester":
                user.setSalary(20000);
                break;
            default:
                throw new IllegalArgumentException("Unknown designation: " + user.getDesignation());
        }
    }
}
