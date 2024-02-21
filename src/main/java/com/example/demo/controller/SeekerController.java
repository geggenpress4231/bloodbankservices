package com.example.demo.controller;
import com.example.demo.model.Seeker;
import com.example.demo.service.SeekerService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<Seeker> getAllSeekers() {
        return seekerService.findAllSeekers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seeker> getSeekerById(@PathVariable Long id) {
        Optional<Seeker> seeker = seekerService.findSeekerById(id);
        return seeker.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Seeker createSeeker(@RequestBody Seeker seeker) {
        return seekerService.saveSeeker(seeker);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seeker> updateSeeker(@PathVariable Long id, @RequestBody Seeker seekerDetails) {
        Optional<Seeker> seekerOptional = seekerService.findSeekerById(id);
        if (seekerOptional.isPresent()) {
            Seeker seekerToUpdate = seekerOptional.get();
            seekerToUpdate.setName(seekerDetails.getName());
            seekerToUpdate.setBloodType(seekerDetails.getBloodType());
            // Add more fields to update as necessary
            Seeker updatedSeeker = seekerService.saveSeeker(seekerToUpdate);
            return ResponseEntity.ok(updatedSeeker);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeeker(@PathVariable Long id) {
        Optional<Seeker> seeker = seekerService.findSeekerById(id);
        if (seeker.isPresent()) {
            seekerService.deleteSeeker(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
