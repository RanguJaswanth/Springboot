package com.example.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.User;
import com.example.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Create user with designation-based salary
    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        // Set the salary based on the designation when creating the user
        return userService.createUser(user);
    }

    // Display user by ID
    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    // Raise salary based on designation
    @PutMapping("/{id}/raise-salary")
    public String raiseSalary(@PathVariable Long id) {
        Optional<User> userOpt = userService.raiseSalaryBasedOnDesignation(id);
        if (userOpt.isPresent()) {
            return "Salary raised successfully! New salary: " + userOpt.get().getSalary();
        } else {
            return "User not found!";
        }
    }

    // Exit with a thank you message (mock behavior, just a GET endpoint for this purpose)
    @GetMapping("/exit")
    public String exitApplication() {
        return "Thank you for using the application!";
    }
}
