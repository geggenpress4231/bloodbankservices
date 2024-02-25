package com.example.demo.repository;

import com.example.demo.model.Donor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DonorRepository extends JpaRepository<Donor, Long> {

    Optional<Donor> findByUserId(Long id);

    @Query("SELECT d FROM Donor d JOIN d.user u WHERE u.id = :userId")
    Optional<Donor> findNameAndBloodTypeByUserId(Long userId);

    @Query("SELECT d FROM Donor d WHERE d.user.email = :email")
    Optional<Donor> findByUserEmail(String email);

  
}
