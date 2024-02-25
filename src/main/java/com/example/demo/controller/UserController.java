package com.example.demo.controller;

import com.example.demo.model.Donor;
import com.example.demo.model.Seeker;
import com.example.demo.model.User;
import com.example.demo.service.DonorService;
import com.example.demo.service.SeekerService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserService userService;
    private final SeekerService seekerService;
    private final DonorService donorService;

    @Autowired
    public UserController(UserService userService, SeekerService seekerService, DonorService donorService) {
        this.userService = userService;
        this.seekerService = seekerService;
        this.donorService = donorService;
    }

    @GetMapping("/")
    public String showLandingPage() {
        return "landingpage"; // Name of the landing page Thymeleaf template
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Name of the Thymeleaf template
    }

    @PostMapping("/register")
    public String registerUser(User user, Model model) {
        // Check if the email is already in use
        Optional<User> existingUser = userService.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            // Email already in use, return back to registration form with error message
            model.addAttribute("registrationError", "Email is already in use.");
            model.addAttribute("user", new User()); // Optionally, retain entered form data except email
            return "register";
        }
        
        // Email not in use, proceed with registration
        userService.registerUser(user);
        return "redirect:/login"; // Redirect to login page after successful registration
    }

    @GetMapping("/profile/{userId}")
    public String userProfile(@PathVariable Long userId, Model model) {
        // Retrieve user by ID
        User user = userService.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Determine user type
        String userType = user.getUserType();

        // Fetch additional details based on user type
        if ("SEEKER".equals(userType)) {
            model.addAttribute("userDetails", seekerService.findSeekerDetailsByUserId(userId));
        } else if ("DONOR".equals(userType)) {
            model.addAttribute("userDetails", donorService.findDonorDetailsByUserId(userId));
        } else {
            throw new RuntimeException("Invalid user type");
        }
        
       

        return "profile";
    }
    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("userDetails") Object userDetails, HttpSession session) {
        if (userDetails instanceof Seeker) {
            Seeker seekerDetails = (Seeker) userDetails;
            seekerService.saveOrUpdateSeeker(seekerDetails);
        } else if (userDetails instanceof Donor) {
            Donor donorDetails = (Donor) userDetails;
            donorService.saveOrUpdateDonor(donorDetails);
        } else {
            throw new IllegalArgumentException("Invalid user details type");
        }

        // Redirect back to the user profile page
        return "redirect:/profile/{userId}";
    }

  
}
