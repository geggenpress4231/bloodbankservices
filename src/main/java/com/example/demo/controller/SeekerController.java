package com.example.demo.controller;

import com.example.demo.model.Seeker;
import com.example.demo.service.SeekerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/seekers")
public class SeekerController {

    private final SeekerService seekerService;

    @Autowired
    public SeekerController(SeekerService seekerService) {
        this.seekerService = seekerService;
    }

    // Get all seekers
    @GetMapping
    public List<Seeker> getAllSeekers() {
        return seekerService.findAllSeekers();
    }

    // Get a seeker by ID
    @GetMapping("/{id}")
    public ResponseEntity<Seeker> getSeekerById(@PathVariable("id") Long seekerId) {
        return seekerService.findSeekerById(seekerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new seeker
    @PostMapping
    public ResponseEntity<?> createSeeker(@RequestBody Seeker newSeeker) {
        try {
            Seeker savedSeeker = seekerService.saveOrUpdateSeeker(newSeeker);
            return ResponseEntity.ok(savedSeeker);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Update an existing seeker
    @PutMapping("/{id}")
    public ResponseEntity<Seeker> updateSeeker(@PathVariable("id") Long seekerId, @RequestBody Seeker updatedSeeker) {
        updatedSeeker.setId(seekerId);
        try {
            Seeker savedSeeker = seekerService.saveOrUpdateSeeker(updatedSeeker);
            return ResponseEntity.ok(savedSeeker);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a seeker
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSeeker(@PathVariable("id") Long seekerId) {
        seekerService.deleteSeeker(seekerId);
        return ResponseEntity.ok().build();
    }
}
