package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "seekers")
public class Seeker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String bloodType;
    

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // Default constructor
    public Seeker() {
    }

    // Constructor without ID (ID is auto-generated)
    public Seeker(String name, String bloodType, User user) {
        this.name = name;
        this.bloodType = bloodType;
        this.user = user;
       
    }

    // Getters
   

    public String getName() {
        return name;
    }

    public String getBloodType() {
        return bloodType;
    }

    public User getUser() {
        return user;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public void setUser(User user) {
        this.user = user;
    }

	public void setId(Long id) {
		this.id=id;
		
	}
	public long getId() {
		return id;
	}

	
}
