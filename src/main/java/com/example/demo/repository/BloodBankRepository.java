package com.example.demo.repository;



import com.example.demo.model.BloodBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodBankRepository extends JpaRepository<BloodBank, Long> {
    // Custom query methods can be defined here
}
