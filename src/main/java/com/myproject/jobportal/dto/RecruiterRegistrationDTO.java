package com.myproject.jobportal.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class RecruiterRegistrationDTO {

	private Long companyId;
    private String companyName;
    private String companyWebsite;
    private String industryType;
    private String companySize;
    private String companyDescription;
    private MultipartFile companyLogo;
    private String recruiterName;
    private String recruiterEmail;
    private String recruiterPhone;
    private String recruiterDesignation;
    private String alternateContact;
    private String country;
    private String state;
    private String city;
    private String streetAddress;
    private String postalCode;
    private String password;
    private String confirmPassword;
    private String gstNumber;
    private String panNumber;
    private String linkedinProfile;
    private String hiringPlan;
}
