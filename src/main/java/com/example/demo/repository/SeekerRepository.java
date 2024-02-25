package com.example.demo.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Donor;
import com.example.demo.model.Seeker;

public interface SeekerRepository extends JpaRepository<Seeker, Long> {

	  Optional<Seeker> findByUserId(Long id);

	    @Query("SELECT s FROM Seeker s JOIN s.user u WHERE u.id = :userId")
	    Optional<Seeker> findNameAndBloodTypeByUserId(Long userId);

	    @Query("SELECT s FROM Seeker s WHERE s.user.email = :email")
	    Optional<Seeker> findByUserEmail(String email);

}
