	package com.example.demo.service;
	
	import com.example.demo.model.Seeker;
	import com.example.demo.repository.SeekerRepository;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;
	
	import java.util.List;
	import java.util.Optional;
	
	@Service
	public class SeekerService {
	
	    private final SeekerRepository seekerRepository;
	
	    @Autowired
	    public SeekerService(SeekerRepository seekerRepository) {
	        this.seekerRepository = seekerRepository;
	    }
	
	    public List<Seeker> findAllSeekers() {
	        return seekerRepository.findAll();
	    }
	
	    public Optional<Seeker> findSeekerById(Long id) {
	        return seekerRepository.findById(id);
	    }
	
	    public Seeker saveSeeker(Seeker seeker) {
	        return seekerRepository.save(seeker);
	    }
	
	    public void deleteSeeker(Long id) {
	        seekerRepository.deleteById(id);
	    }
	
	    
	}
