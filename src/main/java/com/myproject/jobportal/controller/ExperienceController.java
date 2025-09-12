package com.myproject.jobportal.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.jobportal.entity.Experience;
import com.myproject.jobportal.services.ExperienceService;

@RestController
@RequestMapping("/experience")
public class ExperienceController {
	
	@Autowired
	private ExperienceService service;
	
	@PostMapping("/add")
	public Experience addExperience(@RequestBody Experience experience,Principal principal) {
		return service.addExperience(experience,principal.getName());
	}
	
	@PutMapping("/update/{experienceId}")
	public Experience update(@RequestBody Experience experience,@PathVariable Long experienceId) {
		return service.update(experienceId,experience);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteExperience(@PathVariable Long id, Principal principal) {
		return service.deleteExperience(id);
	}

}
