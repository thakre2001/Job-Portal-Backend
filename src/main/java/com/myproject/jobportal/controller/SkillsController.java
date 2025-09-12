package com.myproject.jobportal.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.jobportal.dto.UserResponseDto;
import com.myproject.jobportal.entity.Skills;
import com.myproject.jobportal.entity.User;
import com.myproject.jobportal.mapper.UserMapper;
import com.myproject.jobportal.services.SkillsService;

@RestController
@RequestMapping("/skills")
public class SkillsController {
	
	@Autowired
	private SkillsService service;
	
	@Autowired
	private UserMapper userMapper;
	
	
	@PostMapping("/add")
	public List<Skills> addSkills(@RequestBody List<Skills> skills) {
		return service.addSkills(skills);
	}
	
	@GetMapping("/get")
	public ResponseEntity<?> getAllSkills() {
		List<Skills> allSkills = service.getAllSkills();
		return ResponseEntity.ok(allSkills);
	}
	
	@DeleteMapping("/delete")
	public void deleteSkills(@RequestParam(name = "id") Long id, Principal principal) {
		service.deleteSkills(id,principal.getName());
	}
	
	@PostMapping("/addSkill/{skillId}")
	public ResponseEntity<?> addSkillToUser(@PathVariable Long skillId,Principal principal) {
		
		 User updatedUser = service.addSkillToUser(skillId,principal.getName());
		 
		 UserResponseDto dto=userMapper.userToUserResponseDto(updatedUser);
		 
		 return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping("/removeSkill/{skillId}")
	public ResponseEntity<UserResponseDto> removeSkillFromUser(@PathVariable Long skillId, Principal principal) {
		User updateUser = service.removeSkillFromUser(skillId,principal.getName());
		
		UserResponseDto dto=userMapper.userToUserResponseDto(updateUser);
		
		return ResponseEntity.ok(dto);
	}
	
}
