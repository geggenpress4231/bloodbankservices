package com.example.demo.controller;

import com.example.demo.model.BloodBank;
import com.example.demo.model.User;
import com.example.demo.service.BloodBankService;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

@Controller // Use @Controller for mixed mode operation
@RequestMapping("/bloodbanks")
public class BloodBankController {

    private final BloodBankService bloodBankService;
    private final UserService userService; // Inject UserService

    @Autowired
    public BloodBankController(BloodBankService bloodBankService, UserService userService) {
        this.bloodBankService = bloodBankService;
        this.userService = userService; // Initialize UserService
    }

    @GetMapping
    @ResponseBody // Use @ResponseBody for RESTful response
    public List<BloodBank> getAllBloodBanks() {
        return bloodBankService.findAllBloodBanks();
    }

    @GetMapping("/{id}")
    @ResponseBody // Use @ResponseBody for RESTful response
    public ResponseEntity<BloodBank> getBloodBankById(@PathVariable Long id) {
        return bloodBankService.findBloodBankById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseBody // Use @ResponseBody for RESTful response
    public ResponseEntity<BloodBank> addBloodBank(@RequestBody BloodBank bloodBank) {
        BloodBank savedBloodBank = bloodBankService.addBloodBank(bloodBank);
        return ResponseEntity.ok(savedBloodBank);
    }

    @DeleteMapping("/{id}")
    @ResponseBody // Use @ResponseBody for RESTful response
    public ResponseEntity<?> deleteBloodBank(@PathVariable Long id) {
        try {
            bloodBankService.deleteBloodBank(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/bloodbankinfo")
    public String bloodBankInfo(Model model, HttpSession session) {
        // Get user ID from the login session
        Long userId = (Long) session.getAttribute("userId");

        // Retrieve user details based on user ID
        Optional<User> userDetails = userService.findById(userId);

        // Add user details to the model
        userDetails.ifPresent(user -> model.addAttribute("userDetails", user));

        // Add blood banks to the model
        model.addAttribute("bloodBanks", bloodBankService.findAllBloodBanks());

        return "bloodbankinfo";
    }

}
