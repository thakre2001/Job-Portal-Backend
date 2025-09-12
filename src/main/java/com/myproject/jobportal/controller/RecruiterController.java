package com.myproject.jobportal.controller;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.jobportal.dto.RecruiterRegistrationDTO;
import com.myproject.jobportal.entity.User;
import com.myproject.jobportal.services.RecruiterService;

@RestController
@RequestMapping("/recruiter")
public class RecruiterController {
	
	@Autowired
	private RecruiterService recruiterService;
	
	@PostMapping(value = "/add")
	public ResponseEntity<?> addRecruiter(@RequestBody RecruiterRegistrationDTO dto) throws IOException {
		return recruiterService.create(dto);
	}
	
	public void update(RecruiterRegistrationDTO dto,Principal principal) {
		
	}

}
