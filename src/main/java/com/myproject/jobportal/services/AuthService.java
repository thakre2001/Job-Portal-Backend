package com.myproject.jobportal.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myproject.jobportal.dto.UserResponseDto;
import com.myproject.jobportal.entity.Experience;
import com.myproject.jobportal.entity.LoginRequest;
import com.myproject.jobportal.entity.User;
import com.myproject.jobportal.mapper.UserMapper;
import com.myproject.jobportal.repository.UserRepository;
import com.myproject.jobportal.security.JwtUtil;

@Service
public class AuthService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository repository;

	public ResponseEntity<?> saveUser(User user) {
		Optional<User> byEmail = repository.findByEmail(user.getEmail());
		if (byEmail.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in Use");
		}
		if (user.getExperience() != null) {
			user.getExperience().forEach(exp -> exp.setUser(user));
		}
		User savedUser = repository.save(user);

		String token = jwtUtil.generateToken(savedUser.getEmail());

		UserResponseDto userDto = userMapper.userToUserResponseDto(savedUser);

		return ResponseEntity.ok().body(Map.of("token", token, "user", userDto));
	}

	public ResponseEntity<?> login(LoginRequest loginRequest) {
		Optional<User> userGot = repository.findByEmail(loginRequest.getEmail());

		if (userGot.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		User user = userGot.get();
		if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credientials");
		}

		String token = jwtUtil.generateToken(user.getEmail());

		UserResponseDto userDto = userMapper.userToUserResponseDto(user);

		return ResponseEntity.ok().body(Map.of("token", token, "user", userDto));
	}
}
