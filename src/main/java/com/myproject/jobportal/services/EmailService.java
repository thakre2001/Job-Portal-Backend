package com.myproject.jobportal.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
	private final JavaMailSender javaMailSender;
	
	public void sendMail(String to, String subject, String body) {
		SimpleMailMessage message=new SimpleMailMessage();
		
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		message.setFrom("shubhamthakre180@gmail.com");
		javaMailSender.send(message);
	}
}
