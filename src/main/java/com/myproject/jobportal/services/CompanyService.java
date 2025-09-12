package com.myproject.jobportal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myproject.jobportal.dto.CompanyDTO;
import com.myproject.jobportal.entity.Company;
import com.myproject.jobportal.entity.User;
import com.myproject.jobportal.mapper.CompanyMapper;
import com.myproject.jobportal.repository.CompanyRepository;
import com.myproject.jobportal.repository.UserRepository;

@Service
public class CompanyService {
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CompanyMapper companyMapper;
	
	public void updateCompany(Long id, Company company, String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(()-> new UsernameNotFoundException("User not found"));
		
		Company companyById=companyRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Company not found"));
		
		if(!companyById.getCreatedBy().getId().equals(user.getId())) {
			throw new RuntimeException("You are not allowed to update this company");
		}
		
		
		
	}

	public List<CompanyDTO> getAllComapnies() {
		
		return companyRepository.findAll()
				.stream().map(companyMapper::toDto).toList();
	}

}
