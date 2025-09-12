package com.myproject.jobportal.dto;

import java.time.LocalDate;

import com.myproject.jobportal.entity.ApplicationStatus;
import com.myproject.jobportal.entity.JobStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobApplicationResponseDTO {
    private Long id;
    private Long jobId;
    private String jobTitle;
    private JobStatus jobStatus;
    private Long applicantId;
    private String applicantName;
    private String applicantEmail;
    private ApplicationStatus status;
    private LocalDate appliedAt;
    private String companyName;
    
}

