package com.myproject.jobportal.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class JobRequestDto {
    private String jobTitle;
    private String jobDescription;
    private List<String> jobLocations;
    private String employmentType;
    private String jobCategory;
    private String jobRole;
    private String industryType;
    private String workMode;
    private Integer noOfOpenings;
    private String shiftTime;
    private LocalDate joiningDate;
    private LocalDate applicationDeadline;

    private String educationMin;
    private List<String> prefferedQualification;
    private Integer experienceMin;
    private Integer experienceMax;
    private List<String> skills;
    private List<String> languageKnown;
    private String noticePeriod;
    private List<String> certificationRequired;

    private String applicationMode;
    private String applicationLink;
    private Boolean requiredResumeUpload;
    private Boolean requiredCoverLetter;

    private Integer salaryMin;
    private Integer salaryMax;
    private Boolean salaryNegotiable;
    private String bonuses;
    private List<String> perks;
}

