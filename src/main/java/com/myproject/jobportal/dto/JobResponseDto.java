package com.myproject.jobportal.dto;

import java.sql.Date;
import java.util.List;

import com.myproject.jobportal.entity.Address;
import com.myproject.jobportal.entity.JobStatus;

import lombok.Data;

@Data
public class JobResponseDto {
    private Long jobId;
    private String jobTitle;
    private String jobDescription;
    private List<String> jobLocations;
    private String employmentType;
    private String jobCategory;
    private String jobRole;
    private String industryType;
    private String workMode;
    private Integer noOfOpenings;
    private Date joiningDate;
    private Date applicationDeadline;
    private String educationMin;
    private Integer experienceMin;
    private Integer experienceMax;
    private List<String> skills;
    private Double salaryMin;
    private Double salaryMax;
    private Boolean salaryNegotiable;
    private List<String> perks;
    private Date postedAt;
    private JobStatus status;
    private Integer applicationCount;
    
    private boolean alreadyApplied;
    
    private String recruiterName;
    private String recruiterEmail;

    private String companyName;
    private String companyLogo;
    private Address companyAddress;
}

