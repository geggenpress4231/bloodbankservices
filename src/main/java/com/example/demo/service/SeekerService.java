package com.example.demo.service;

import com.example.demo.model.Seeker;
import com.example.demo.model.User;
import com.example.demo.repository.SeekerRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SeekerService {

    private final SeekerRepository seekerRepository;
    private final UserRepository userRepository;

    @Autowired
    public SeekerService(SeekerRepository seekerRepository, UserRepository userRepository) {
        this.seekerRepository = seekerRepository;
        this.userRepository = userRepository;
    }

    public List<Seeker> findAllSeekers() {
        return seekerRepository.findAll();
    }

    public Optional<Seeker> findSeekerById(Long id) {
        return seekerRepository.findById(id);
    }

    public Seeker saveSeeker(Seeker seeker) {
        // Example of additional logic before saving a seeker
        // This could involve creating or updating the associated User entity
        User user = seeker.getUser();
        if (user != null && user.getId() == null) { // Assuming new User needs to be saved
            userRepository.save(user); // Save User to get an ID
        }
        return seekerRepository.save(seeker); // Save Seeker with associated User
    }

    public void deleteSeeker(Long id) {
        seekerRepository.deleteById(id);
    }

    // Method to find a seeker by user's email - Example of integrating user search
    public Optional<Seeker> findSeekerByEmail(String email) {
        return userRepository.findByEmail(email)
                .flatMap(user -> seekerRepository.findByUserId(user.getId()));
    }

    public Optional<Seeker> findSeekerByUserId(Long userId) {
        return seekerRepository.findByUserId(userId);
    }

    // Additional method for finding seeker details by user ID
    public Optional<Seeker> findSeekerDetailsByUserId(Long userId) {
        return seekerRepository.findNameAndBloodTypeByUserId(userId);
    }

    public Seeker saveOrUpdateSeeker(Seeker seekerDetails) {
        // Check if the seeker has an ID
        if (Objects.isNull(seekerDetails.getId())) {
            // If the seeker ID is null, it means it's a new seeker, so we save it
            return saveSeeker(seekerDetails);
        } else {
            // If the seeker ID is not null, it means it's an existing seeker, so we update it
            Optional<Seeker> existingSeekerOptional = findSeekerById(seekerDetails.getId());
            if (existingSeekerOptional.isPresent()) {
                // Merge the updated details with the existing seeker
                Seeker existingSeeker = existingSeekerOptional.get();
                existingSeeker.setName(seekerDetails.getName());
                existingSeeker.setBloodType(seekerDetails.getBloodType());
                // You can update other fields as needed

                // Save the updated seeker
                return seekerRepository.save(existingSeeker);
            } else {
                throw new IllegalArgumentException("Seeker with ID " + seekerDetails.getId() + " not found");
            }
        }
    }

    // Additional business logic and methods can be added here
}
