package com.myproject.jobportal.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.jobportal.entity.User;
import com.myproject.jobportal.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;

}
