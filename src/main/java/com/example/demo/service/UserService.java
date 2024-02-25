package com.example.demo.service;


import com.example.demo.model.Donor;
import com.example.demo.model.Seeker;
import com.example.demo.model.User;
import com.example.demo.repository.DonorRepository;
import com.example.demo.repository.SeekerRepository;
import com.example.demo.repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final DonorRepository donorRepository;
    private final SeekerRepository seekerRepository;

    @Autowired
    public UserService(UserRepository userRepository, DonorRepository donorRepository, SeekerRepository seekerRepository) {
        this.userRepository = userRepository;
        this.donorRepository = donorRepository;
        this.seekerRepository = seekerRepository;
    }

    public User registerUser(User user) {
        User savedUser = userRepository.save(user);
        
        if ("DONOR".equals(user.getUserType())) {
            Donor donor = new Donor();
            donor.setUser(savedUser);
            // Set other donor-specific fields here
            donorRepository.save(donor);
        } else if ("SEEKER".equals(user.getUserType())) {
            Seeker seeker = new Seeker();
            seeker.setUser(savedUser);
            // Set other seeker-specific fields here
            seekerRepository.save(seeker);
        }
        
        return savedUser;
    }

    public Optional<User> validateUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }
        return Optional.empty();
    }
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    
	}

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

	
}


