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

import com.myproject.jobportal.entity.LoginRequest;
import com.myproject.jobportal.entity.User;
import com.myproject.jobportal.repository.UserRepository;
import com.myproject.jobportal.security.JwtUtil;

@Service
public class AuthService {

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
		User savedUser = repository.save(user);

		String token = jwtUtil.generateToken(savedUser.getEmail());

		User userDto = new User();
		userDto.setName(savedUser.getName());
		userDto.setEmail(savedUser.getEmail());
		userDto.setMobile(savedUser.getMobile());
		userDto.setWorkStatus(savedUser.getWorkStatus());

		return ResponseEntity.ok().body(Map.of("token", token, "user", userDto));
	}

	public User findUser(Long id) {
		Optional<User> userById = repository.findById(id);
		if (userById.isPresent()) {
			return userById.get();
		}
		return null;
	}

	public ResponseEntity<?> login(LoginRequest loginRequest) {
		Optional<User> userGot = repository.findByEmail(loginRequest.getEmail());

		if (userGot.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
		if (!passwordEncoder.matches(loginRequest.getPassword(), userGot.get().getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credientials");
		}

		User user = userGot.get();

		String token = jwtUtil.generateToken(user.getEmail());

		User userDto = new User();
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setMobile(user.getMobile());
		userDto.setWorkStatus(user.getWorkStatus());
		userDto.setProfilePhoto(user.getProfilePhoto());
		userDto.setResume(user.getResume());

		return ResponseEntity.ok().body(Map.of("token", token, "user", userDto));
	}

	public List<User> getAllUser() {
		return repository.findAll();
	}

	public User uploadProfilePhoto(String email, byte[] image) {
		Optional<User> user = repository.findByEmail(email);

		if (user.isEmpty()) {
			throw new UsernameNotFoundException("User not found");
		}

		User userGot = user.get();

		if (image != null) {
			userGot.setProfilePhoto(image);
		}

		User savedUser = repository.save(userGot);

		User userDto = new User();

		userDto.setName(savedUser.getName());
		userDto.setEmail(savedUser.getEmail());
		userDto.setMobile(savedUser.getMobile());
		userDto.setRole(savedUser.getRole());
		userDto.setWorkStatus(savedUser.getWorkStatus());
		userDto.setProfilePhoto(savedUser.getProfilePhoto());
		userDto.setResume(savedUser.getResume());
		return userDto;

	}

	public User updateUser(String email, User user) {
		Optional<User> existingUser = repository.findByEmail(email);

		if (existingUser.isEmpty()) {
			throw new UsernameNotFoundException("user not found");
		}

		User userGot = existingUser.get();

		if (user.getName() != null) {
			userGot.setName(user.getName());
		}
		if (user.getEmail() != null) {
			userGot.setEmail(user.getEmail());
		}
		if (user.getMobile() != null) {
			userGot.setMobile(user.getMobile());
		}
		if (user.getWorkStatus() != null) {
			userGot.setWorkStatus(user.getWorkStatus());
		}

		User savedUser = repository.save(userGot);

		User userDto = new User();

		userDto.setName(savedUser.getName());
		userDto.setEmail(savedUser.getEmail());
		userDto.setMobile(savedUser.getMobile());
		userDto.setWorkStatus(savedUser.getWorkStatus());
		userDto.setProfilePhoto(savedUser.getProfilePhoto());
		userDto.setRole(savedUser.getRole());
		userDto.setResume(savedUser.getResume());

		return userDto;
	}

	public User getUserProfile(String name) {
		Optional<User> byEmail = repository.findByEmail(name);
		User user = byEmail.get();

		User userDto = new User();

		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setMobile(user.getMobile());
		userDto.setWorkStatus(user.getWorkStatus());
		userDto.setProfilePhoto(user.getProfilePhoto());
		userDto.setRole(user.getRole());
		userDto.setResume(user.getResume());

		return userDto;
	}

	public User uploadResume(byte[] bytes, String email) {
		Optional<User> byEmail = repository.findByEmail(email);

		if (byEmail.isEmpty()) {
			throw new UsernameNotFoundException("User not found");
		}

		User user = byEmail.get();

		if (bytes != null) {
			user.setResume(bytes);
		}
		
		User savedUser = repository.save(user);
		
		User userDto=new User();
		
		userDto.setName(savedUser.getName());
		userDto.setEmail(savedUser.getEmail());
		userDto.setMobile(savedUser.getMobile());
		userDto.setWorkStatus(savedUser.getWorkStatus());
		userDto.setProfilePhoto(savedUser.getProfilePhoto());
		userDto.setRole(savedUser.getRole());
		userDto.setResume(savedUser.getResume());
		
		return userDto;

	}

}
