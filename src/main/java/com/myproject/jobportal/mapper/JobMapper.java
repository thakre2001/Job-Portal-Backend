package com.myproject.jobportal.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.myproject.jobportal.dto.JobRequestDto;
import com.myproject.jobportal.dto.JobResponseDto;
import com.myproject.jobportal.entity.Job;

@Mapper(componentModel = "spring")
public interface JobMapper {

    // DTO -> Entity
    Job toEntity(JobRequestDto dto);

    // Entity -> DTO
    @Mapping(target = "recruiterName", source = "postedBy.name")
    @Mapping(target = "recruiterEmail", source = "postedBy.email")
    @Mapping(target = "companyName", source =  "company.companyName")
    @Mapping(target = "companyLogo", source = "company.companyLogo")
    @Mapping(target = "companyAddress", source = "company.address")
    JobResponseDto toDto(Job job);

	List<JobResponseDto> toDtoList(List<Job> jobs);
}

