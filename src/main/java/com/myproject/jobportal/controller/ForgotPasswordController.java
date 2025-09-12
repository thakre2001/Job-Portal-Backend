package com.myproject.jobportal.controller;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.myproject.jobportal.dto.EmailRequest;
import com.myproject.jobportal.dto.OtpRequest;
import com.myproject.jobportal.dto.ResetPasswordRequest;
import com.myproject.jobportal.entity.User;
import com.myproject.jobportal.repository.UserRepository;
import com.myproject.jobportal.services.EmailService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/password")
@RequiredArgsConstructor
public class ForgotPasswordController {

	private final UserRepository userRepository;
	private final EmailService emailService;
	private final PasswordEncoder passwordEncoder;

	@PostMapping("/forgot-password")
	public ResponseEntity<?> forgotPassword(@RequestBody EmailRequest request) {
		String email = request.getEmail();

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

		String otp = String.valueOf(new SecureRandom().nextInt(900_000) + 100_000);
		user.setResetOtp(otp);
		user.setResetOtpExpiry(LocalDateTime.now().plusMinutes(10));
		userRepository.save(user);

		emailService.sendMail(email, "Password Reset OTP", "Your OTP is: " + otp + " (valid for 10 minutes)");

		return ResponseEntity.ok(Map.of("message", "OTP sent successfully"));
	}

	@PostMapping("/verify-otp")
	public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest request) {
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

		if (user.getResetOtp() == null || user.getResetOtpExpiry() == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No OTP request found");

		if (LocalDateTime.now().isAfter(user.getResetOtpExpiry()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP expired");

		if (!user.getResetOtp().equals(request.getOtp()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid OTP");

		return ResponseEntity.ok(Map.of("message", "OTP verified"));
	}

	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {

		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

		if (user.getResetOtp() == null || user.getResetOtpExpiry() == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No OTP request found");

		if (LocalDateTime.now().isAfter(user.getResetOtpExpiry()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP expired");

		if (!user.getResetOtp().equals(request.getOtp()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid OTP");

		user.setPassword(passwordEncoder.encode(request.getNewPassword()));

		user.setResetOtp(null);
		user.setResetOtpExpiry(null);

		userRepository.save(user);

		return ResponseEntity.ok(Map.of("message", "Password updated successfully"));

	}
}
