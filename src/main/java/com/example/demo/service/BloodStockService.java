package com.example.demo.service;



import com.example.demo.model.BloodBank;
import com.example.demo.model.BloodStock;
import com.example.demo.repository.BloodBankRepository;
import com.example.demo.repository.BloodStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BloodStockService {

    private final BloodStockRepository bloodStockRepository;
    private final BloodBankRepository bloodBankRepository;

    @Autowired
    public BloodStockService(BloodStockRepository bloodStockRepository, BloodBankRepository bloodBankRepository) {
        this.bloodStockRepository = bloodStockRepository;
        this.bloodBankRepository = bloodBankRepository;
    }

    public BloodStock addBloodStock(Long bloodBankId, BloodStock bloodStock) {
        // Find the blood bank by ID
        BloodBank bloodBank = bloodBankRepository.findById(bloodBankId)
                .orElseThrow(() -> new RuntimeException("Blood Bank not found"));
        
        // Set the blood bank to the blood stock
        bloodStock.setBloodBank(bloodBank);
        
        // Save the blood stock
        return bloodStockRepository.save(bloodStock);
    }

    public List<BloodStock> findAllStocksByBloodBank(Long bloodBankId) {
        // Implement based on your repository method
        // This is a placeholder implementation
        return bloodStockRepository.findByBloodBankId(bloodBankId);
    }

    public Optional<BloodStock> findBloodStockById(Long id) {
        return bloodStockRepository.findById(id);
    }

   
}
