package com.myproject.jobportal.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myproject.jobportal.dto.UserResponseDto;
import com.myproject.jobportal.entity.LoginRequest;
import com.myproject.jobportal.entity.User;
import com.myproject.jobportal.mapper.UserMapper;
import com.myproject.jobportal.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserRepository repository;

	public User findUser(Long id) {
		Optional<User> userById = repository.findById(id);
		if (userById.isPresent()) {
			return userById.get();
		}
		return null;
	}

	public List<User> getAllUser() {
		return repository.findAll();
	}

	public UserResponseDto uploadProfilePhoto(String email, byte[] image) {
		User user = repository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found"));

		if (image != null) {
			user.setProfilePhoto(image);
		}

		return userMapper.userToUserResponseDto(repository.save(user));

	}

	public UserResponseDto updateUser(String email, User user) {
		User userGot = repository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found"));

		if (user.getName() != null) {
			userGot.setName(user.getName());
		}
		if (user.getMobile() != null) {
			userGot.setMobile(user.getMobile());
		}
		if (user.getWorkStatus() != null) {
			userGot.setWorkStatus(user.getWorkStatus());
		}

		return userMapper.userToUserResponseDto(repository.save(userGot));

		
	}

	public UserResponseDto getUserProfile(String name) {
		User byEmail = repository.findByEmail(name).orElseThrow(()->new UsernameNotFoundException("User not found"));
		
		return userMapper.userToUserResponseDto(byEmail);
	}

	public UserResponseDto uploadResume(byte[] bytes, String email) {
		User user = repository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found"));

		if (bytes != null) {
			user.setResume(bytes);
		}

		return userMapper.userToUserResponseDto(repository.save(user));

	}

}
