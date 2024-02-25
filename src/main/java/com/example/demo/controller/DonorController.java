package com.example.demo.controller;

import com.example.demo.model.Donor;
import com.example.demo.service.DonorService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/donors")
public class DonorController {

    private final DonorService donorService;

    @Autowired
    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @GetMapping("/{id}")
    public String getDonorById(@PathVariable Long id, Model model) {
        Optional<Donor> donor = donorService.findDonorById(id);
        if (donor.isPresent()) {
            model.addAttribute("donor", donor.get());
            return "donor/details"; // Assuming you have a Thymeleaf template for showing donor details
        } else {
            return "redirect:/donors/not-found"; // Or handle the not found case as you see fit
        }
    }

    // Example endpoint for finding a donor by User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<Donor> getDonorByUserId(@PathVariable Long userId) {
        Optional<Donor> donor = donorService.findDonorByUserId(userId);
        return donor.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Additional methods for creating, updating, and deleting donors
}
