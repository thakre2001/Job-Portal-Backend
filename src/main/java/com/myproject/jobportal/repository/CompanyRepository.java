package com.myproject.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.jobportal.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{

}
