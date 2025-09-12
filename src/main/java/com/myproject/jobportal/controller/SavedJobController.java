package com.myproject.jobportal.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.jobportal.dto.SavedJobResponseDto;
import com.myproject.jobportal.services.SavedJobService;

@RestController
@RequestMapping("/savedjobs")
public class SavedJobController {
	
	@Autowired
	private SavedJobService savedJobService;
	
	@PostMapping("/add/{jobId}")
	public ResponseEntity<SavedJobResponseDto> saveJob(@PathVariable Long jobId,Principal principal) {
		return ResponseEntity.ok(savedJobService.saveJob(jobId,principal.getName()));
	}
	
	@DeleteMapping("/delete/{jobId}")
	public ResponseEntity<?> removeSavedJob(@PathVariable Long jobId,Principal principal){
		return savedJobService.removeSavedJob(jobId,principal.getName());
	}

	@GetMapping("/getAll")
	public List<SavedJobResponseDto> getSavedJobsByUser(Principal principal) {
		return savedJobService.getSavedJobsByUser(principal.getName());
		
		
	}

}
