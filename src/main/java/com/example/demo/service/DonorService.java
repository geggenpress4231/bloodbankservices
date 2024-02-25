package com.example.demo.service;

import com.example.demo.model.Donor;
import com.example.demo.model.User;
import com.example.demo.repository.DonorRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DonorService {

    private final DonorRepository donorRepository;
    private final UserRepository userRepository; // Assume this is added for user management

    @Autowired
    public DonorService(DonorRepository donorRepository, UserRepository userRepository) {
        this.donorRepository = donorRepository;
        this.userRepository = userRepository;
    }

    public List<Donor> findAllDonors() {
        return donorRepository.findAll();
    }

    public Optional<Donor> findDonorById(Long id) {
        return donorRepository.findById(id);
    }

    public Donor saveDonor(Donor donor) {
        // Example of additional logic before saving a donor
        // This could involve creating or updating the associated User entity
        User user = donor.getUser();
        if (user != null && user.getId() == null) { // Assuming new User needs to be saved
            userRepository.save(user); // Save User to get an ID
        }
        return donorRepository.save(donor); // Save Donor with associated User
    }

    public void deleteDonor(Long id) {
        donorRepository.deleteById(id);
    }

    // Method to find a donor by user's email - Example of integrating user search
    public Optional<Object> findDonorByEmail(String email) {
        return userRepository.findByEmail(email)
                .flatMap(user -> donorRepository.findByUserId(user.getId()));
    }

    public Optional<Donor> findDonorByUserId(Long userId) {
        return donorRepository.findByUserId(userId);
    }

 // DonorService.java
    public Optional<Donor> findDonorDetailsByUserId(Long userId) {
        return donorRepository.findNameAndBloodTypeByUserId(userId);
    }

    public void saveOrUpdateDonor(Donor donorDetails) {
        // Check if the donor has an ID
        if (donorDetails.getId() == null) {
            // If the donor ID is null, it means it's a new donor, so we save it
            saveDonor(donorDetails);
        } else {
            // If the donor ID is not null, it means it's an existing donor, so we update it
            Optional<Donor> existingDonorOptional = findDonorById(donorDetails.getId());
            if (existingDonorOptional.isPresent()) {
                // Merge the updated details with the existing donor
                Donor existingDonor = existingDonorOptional.get();
                existingDonor.setName(donorDetails.getName());
                existingDonor.setBloodType(donorDetails.getBloodType());
                // You can update other fields as needed

                // Save the updated donor
                donorRepository.save(existingDonor);
            } else {
                throw new IllegalArgumentException("Donor with ID " + donorDetails.getId() + " not found");
            }
        }
    }



    // Additional business logic and methods can be added here
}
