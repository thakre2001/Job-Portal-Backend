package com.myproject.jobportal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.myproject.jobportal.dto.SavedJobResponseDto;
import com.myproject.jobportal.entity.SavedJob;

@Mapper(componentModel = "spring")
public interface SavedJobMapper {
	
	@Mapping(target = "jobId" , source = "job.jobId")
	@Mapping(target = "jobTitle" , source = "job.jobTitle")
	@Mapping(target = "companyName" , source = "job.company.companyName")
	@Mapping(target = "location" , source = "job.jobLocations")
	@Mapping(target = "employmentType" , source = "job.employmentType")
	SavedJobResponseDto toDto(SavedJob savedJob);
	
	

}
