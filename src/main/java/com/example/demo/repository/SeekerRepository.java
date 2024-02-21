package com.example.demo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Seeker;

public interface SeekerRepository extends JpaRepository<Seeker, Long> {

	List<Seeker> findAll();
    // You can define custom query methods here
}
