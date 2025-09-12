package com.myproject.jobportal.services;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myproject.jobportal.entity.Skills;
import com.myproject.jobportal.entity.User;
import com.myproject.jobportal.entity.UserRole;
import com.myproject.jobportal.repository.SkillsRepository;
import com.myproject.jobportal.repository.UserRepository;

@Service
public class SkillsService {
	
	@Autowired
	private SkillsRepository repository;
	
	@Autowired
	private UserRepository userRepository;

	public List<Skills> addSkills(List<Skills> skills) {
		// TODO Auto-generated method stub
		return repository.saveAll(skills);
	}

	public List<Skills> getAllSkills() {
		return repository.findAll();
	}

	public void deleteSkills(Long id, String name) {
		User byEmail = userRepository.findByEmail(name)
				.orElseThrow(()->new UsernameNotFoundException("User not present"));
		if(byEmail.getRole() == UserRole.ADMIN) {
			repository.deleteById(id);
		}
		
	}

	public User addSkillToUser(Long skillId, String name) {
		User user = userRepository.findByEmail(name)
				.orElseThrow(()->new UsernameNotFoundException("User not present"));
		
		Skills skill=repository.findById(skillId).orElseThrow(() -> new RuntimeException("Skill not found"));
		
		user.getSkills().add(skill);
	
		return userRepository.save(user);
	}

	public User removeSkillFromUser(Long skillId, String name) {
		User user = userRepository.findByEmail(name)
				.orElseThrow(()->new UsernameNotFoundException("User not present"));
		
		Skills skill=repository.findById(skillId).orElseThrow(() -> new RuntimeException("Skill not found"));
		
		user.getSkills().remove(skill);
		
		return userRepository.save(user);
		
	}

}
