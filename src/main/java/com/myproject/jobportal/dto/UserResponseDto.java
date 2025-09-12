package com.myproject.jobportal.dto;

import java.util.List;

import com.myproject.jobportal.entity.Education;
import com.myproject.jobportal.entity.Experience;

import lombok.Data;

@Data
public class UserResponseDto {
	private String name;
	private String email;
	private String role;
	private String mobile;
	private byte[] profilePhoto;
	private String workStatus;
	private List<SkillDto> skills;
	private List<Education> education;
	private List<Experience> experience;
	private byte[] resume;
	private String recruiterDesignation;
	private String alternateContact;
	private CompanyDTO company;
}
