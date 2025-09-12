package com.myproject.jobportal.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myproject.jobportal.dto.RecruiterRegistrationDTO;
import com.myproject.jobportal.dto.UserResponseDto;
import com.myproject.jobportal.entity.Address;
import com.myproject.jobportal.entity.Company;
import com.myproject.jobportal.entity.User;
import com.myproject.jobportal.entity.UserRole;
import com.myproject.jobportal.mapper.UserMapper;
import com.myproject.jobportal.repository.CompanyRepository;
import com.myproject.jobportal.repository.UserRepository;
import com.myproject.jobportal.security.JwtUtil;

@Service
public class RecruiterService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserMapper userMapper;

	public ResponseEntity<?> create(RecruiterRegistrationDTO dto) throws IOException {
		
		Company company;
		
		Boolean isNewCompany=false;
		
		if(dto.getCompanyId() != null) {
			company = companyRepository.findById(dto.getCompanyId()).orElseThrow(()->new RuntimeException("Company not found"));
		}else {
			company=new Company();
			company.setCompanyName(dto.getCompanyName());
			company.setCompanyDescription(dto.getCompanyDescription());
			company.setCompanySize(dto.getCompanySize());
			company.setCompanyWebsite(dto.getCompanyWebsite());
			company.setIndustryType(dto.getIndustryType());
			company.setGstNumber(dto.getGstNumber());
			company.setPanNumber(dto.getPanNumber());
			company.setLinkedinProfile(dto.getLinkedinProfile());
			company.setHiringPlan(dto.getHiringPlan());
			
			if (dto.getCompanyLogo() != null && !dto.getCompanyLogo().isEmpty()) {
		        String fileName = UUID.randomUUID() + "_" + dto.getCompanyLogo().getOriginalFilename();
		        Path path = Paths.get("uploads/company-logos/" + fileName);
		        Files.createDirectories(path, null);
		        Files.write(path, dto.getCompanyLogo().getBytes());

		        company.setCompanyLogo("/uploads/company-logos/" + fileName); // save as string
		    }
			
			Address address=new Address();
			address.setCity(dto.getCity());
			address.setCountry(dto.getCountry());
			address.setPostalCode(dto.getPostalCode());
			address.setState(dto.getState());
			address.setStreetAddress(dto.getStreetAddress());
			
			company.setAddress(address);
			
			company=companyRepository.save(company);
			
			isNewCompany=true;
		}
		
		User recruiter=new User();
		recruiter.setName(dto.getRecruiterName());
		recruiter.setEmail(dto.getRecruiterEmail());
		recruiter.setMobile(dto.getRecruiterPhone());
		recruiter.setRecruiterDesignation(dto.getRecruiterDesignation());
		recruiter.setAlternateContact(dto.getAlternateContact());
		recruiter.setRole(UserRole.RECRUITER);
		recruiter.setPassword(passwordEncoder.encode(dto.getPassword()));
		recruiter.setCompany(company);
		
		 User savedUser = userRepository.save(recruiter);
		 
		 if(isNewCompany) {
			 company.setCreatedBy(savedUser);
			 companyRepository.save(company);
		 }
		 
		 String token=jwtUtil.generateToken(savedUser.getEmail());
		 
		 UserResponseDto userDTO=userMapper.userToUserResponseDto(savedUser);
		 
		 return ResponseEntity.ok().body(Map.of("token", token, "user", userDTO));
		
		
	}

}
