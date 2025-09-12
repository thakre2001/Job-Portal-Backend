package com.myproject.jobportal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myproject.jobportal.entity.Experience;
import com.myproject.jobportal.entity.User;
import com.myproject.jobportal.repository.ExperienceRepository;
import com.myproject.jobportal.repository.UserRepository;

@Service
public class ExperienceService {

	@Autowired
	private ExperienceRepository repository;

	@Autowired
	private UserRepository userRepository;

	public Experience addExperience(Experience experience, String name) {
		User user = userRepository.findByEmail(name)
				.orElseThrow(() -> new UsernameNotFoundException("User not present"));
		experience.setUser(user);
		return repository.save(experience);
	}

	public Experience update(Long experienceId, Experience experience) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		Experience existingExp = repository.findById(experienceId)
				.orElseThrow(() -> new RuntimeException("Experience not found"));

		if (!existingExp.getUser().getId().equals(user.getId())) {
			throw new RuntimeException("You are not allowed to change this experience");
		}

		existingExp.setCompanyName(experience.getCompanyName());
		existingExp.setJobTitle(experience.getJobTitle());
		existingExp.setFromDate(experience.getFromDate());
		existingExp.setToDate(experience.getToDate());
		existingExp.setDescription(experience.getDescription());

		return repository.save(existingExp);

	}

	public ResponseEntity<?> deleteExperience(Long id) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not present"));

		Experience experience = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Experience not found"));

		if (!experience.getUser().getId().equals(user.getId())) {
			throw new RuntimeException("You are not allowed to delete this experience");
		}

		user.getExperience().remove(experience);

		userRepository.save(user);

		return ResponseEntity.ok("Deleted successfully");
	}

}
