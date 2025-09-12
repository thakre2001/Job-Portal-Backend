package com.myproject.jobportal.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class SavedJobResponseDto {
	private Long id;
	private Long jobId;
	private String jobTitle;
	private String companyName;
	private List<String> location;
	private String employmentType;
	private LocalDate savedAt;
}
