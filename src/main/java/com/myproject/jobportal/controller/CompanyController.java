package com.myproject.jobportal.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.jobportal.dto.CompanyDTO;
import com.myproject.jobportal.entity.Company;
import com.myproject.jobportal.services.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	
	public void updateCompany(@PathVariable Long id,@RequestBody Company company,Principal principal) {
		companyService.updateCompany(id,company,principal.getName());
	}
	
	@GetMapping("/all")
	public List<CompanyDTO> getAllCompanies() {
		return companyService.getAllComapnies();
	}
}
