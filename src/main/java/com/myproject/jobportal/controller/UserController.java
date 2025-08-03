package com.myproject.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.jobportal.entity.User;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

}
