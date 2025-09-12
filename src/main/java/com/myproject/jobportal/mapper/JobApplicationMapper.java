package com.myproject.jobportal.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.myproject.jobportal.dto.JobApplicationResponseDTO;
import com.myproject.jobportal.entity.JobApplication;

@Mapper(componentModel = "spring")
public interface JobApplicationMapper {

    @Mapping(source = "job.jobId", target = "jobId")
    @Mapping(source = "job.jobTitle", target = "jobTitle")
    @Mapping(source = "applicant.id", target = "applicantId")
    @Mapping(source = "applicant.name", target = "applicantName")
    @Mapping(source = "applicant.email", target = "applicantEmail")
    @Mapping(source = "job.status", target = "jobStatus")
    JobApplicationResponseDTO toDto(JobApplication jobApplication);

    List<JobApplicationResponseDTO> toDtoList(List<JobApplication> jobApplications);
}

