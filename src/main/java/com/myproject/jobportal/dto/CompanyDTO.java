package com.myproject.jobportal.dto;

import com.myproject.jobportal.entity.Address;

import lombok.Data;

@Data
public class CompanyDTO {
    private Long id;
    private String companyName;
    private String companyWebsite;
    private String industryType;
    private String companySize;
    private String companyDescription;
    private String companyLogo;
    private Address address;
}

