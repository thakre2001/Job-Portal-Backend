package com.myproject.jobportal.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.myproject.jobportal.dto.UserResponseDto;
import com.myproject.jobportal.entity.User;
import com.myproject.jobportal.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService service;
	
	@PutMapping("/upload-profile")
	public ResponseEntity<?> uploadProfilePhoto(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
		UserResponseDto uploadProfilePhoto = service.uploadProfilePhoto(principal.getName(), file.getBytes());
		return ResponseEntity.ok(uploadProfilePhoto);
	}
	
	
	@PutMapping("/update-user")
	public ResponseEntity<?> updateUser(@RequestBody User user,@AuthenticationPrincipal UserDetails userDetails) {
		
		if(userDetails == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid or missing");
		}
		String email=userDetails.getUsername();
		
		UserResponseDto updateUser = service.updateUser(email,user);
		
		return ResponseEntity.ok(updateUser);
	}
	
	@PutMapping("/upload-resume")
	public ResponseEntity<?> uploadResume(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
		UserResponseDto uploadResume = service.uploadResume(file.getBytes(),principal.getName());
		return ResponseEntity.ok(uploadResume);
	}
	
	@GetMapping("/get-userProfile")
	public ResponseEntity<?> getUserProfile(Principal principal){
		UserResponseDto user=service.getUserProfile(principal.getName());
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/allUser")
	public List<User> getAllUser(){
		return service.getAllUser();
	}
	

}
