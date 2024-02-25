package com.example.demo.service;



import com.example.demo.model.BloodBank;
import com.example.demo.repository.BloodBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BloodBankService {

    private final BloodBankRepository bloodBankRepository;

    @Autowired
    public BloodBankService(BloodBankRepository bloodBankRepository) {
        this.bloodBankRepository = bloodBankRepository;
    }

    public List<BloodBank> findAllBloodBanks() {
        return bloodBankRepository.findAll();
    }

    public Optional<BloodBank> findBloodBankById(Long id) {
        return bloodBankRepository.findById(id);
    }

    public BloodBank addBloodBank(BloodBank bloodBank) {
        return bloodBankRepository.save(bloodBank);
    }
    
    public void deleteBloodBank(Long id) {
        BloodBank bloodBank = bloodBankRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blood Bank not found"));
        bloodBankRepository.delete(bloodBank);
    }

}
