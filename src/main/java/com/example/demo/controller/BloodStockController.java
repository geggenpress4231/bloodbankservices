package com.example.demo.controller;

import com.example.demo.model.BloodStock;
import com.example.demo.model.BloodBank;
import com.example.demo.service.BloodBankService;
import com.example.demo.service.BloodStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/bloodstocks")
public class BloodStockController {

    private final BloodStockService bloodStockService;
	private BloodBankService bloodBankService;

    @Autowired
    public BloodStockController(BloodStockService bloodStockService, BloodBankService bloodBankService) {
        this.bloodStockService = bloodStockService;
        this.bloodBankService = bloodBankService;
    }

    @PostMapping("/add/{bloodBankId}")
    public ResponseEntity<BloodStock> addBloodStock(@PathVariable Long bloodBankId, @RequestBody BloodStock bloodStock) {
        return ResponseEntity.ok(bloodStockService.addBloodStock(bloodBankId, bloodStock));
    }

    @GetMapping("/all/{bloodBankId}")
    public ResponseEntity<List<BloodStock>> findAllStocksByBloodBank(@PathVariable Long bloodBankId) {
        return ResponseEntity.ok(bloodStockService.findAllStocksByBloodBank(bloodBankId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloodStock> findBloodStockById(@PathVariable Long id) {
        return bloodStockService.findBloodStockById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint to update a blood stock - assuming there's a method in BloodStockService
    @PutMapping("/update/{id}")
    public ResponseEntity<BloodStock> updateBloodStock(@PathVariable Long id, @RequestBody BloodStock bloodStock) {
        // Implement the update logic based on your requirements and service layer support
        // Placeholder response
        return ResponseEntity.ok(bloodStock); // This should actually call a service method to update
    }

    // Endpoint to delete a blood stock - assuming there's a method in BloodStockService
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBloodStock(@PathVariable Long id) {
        // Implement the delete logic based on your requirements and service layer support
        // Placeholder response
        return ResponseEntity.ok().build(); // This should actually call a service method to delete
    }

    @GetMapping("/view/all/{bloodBankId}")
    public String viewAllStocksByBloodBank(@PathVariable Long bloodBankId, Model model) {
        List<BloodStock> stocks = bloodStockService.findAllStocksByBloodBank(bloodBankId);
        Optional<BloodBank> optionalBloodBank = bloodBankService.findBloodBankById(bloodBankId);
        
        if (optionalBloodBank.isPresent()) {
            BloodBank bloodBank = optionalBloodBank.get();
            String bloodBankName = bloodBank.getName();
            model.addAttribute("bloodBankName", bloodBankName);
        } else {
            // Blood bank not found, handle the situation accordingly
            model.addAttribute("bloodBankName", "Unknown");
        }
        
        model.addAttribute("bloodStocks", stocks);
        return "bloodstocksinfo"; // Name of the Thymeleaf template
    }
}
