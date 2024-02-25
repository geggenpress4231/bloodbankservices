package com.example.demo.repository;


import com.example.demo.model.BloodStock;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodStockRepository extends JpaRepository<BloodStock, Long> {

	List<BloodStock> findByBloodBankId(Long bloodBankId);
    // Additional query methods can be defined here
}
