package com.myproject.jobportal.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "jobs")
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long jobId;

	private String jobTitle;

	@Column(length = 2000)
	private String jobDescription;

	@ElementCollection
	private List<String> jobLocations;
	
	@Enumerated(EnumType.STRING)
	private JobStatus status=JobStatus.DRAFT;

	private Integer applicationCount = 0;


	private String employmentType;
	private String jobCategory;
	private String jobRole;
	private String industryType;
	private String workMode;
	private Integer noOfOpenings;
	private String shiftTime;

	@Temporal(TemporalType.DATE)
	private Date joiningDate;

	@Temporal(TemporalType.DATE)
	private Date applicationDeadline;

	private String educationMin;

	@ElementCollection
	private List<String> preferredQualification;

	private Integer experienceMin;
	private Integer experienceMax;

	@ElementCollection
	private List<String> skills;

	@ElementCollection
	private List<String> languageKnown;

	private String noticePeriod;

	@ElementCollection
	private List<String> certificationRequired;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User postedBy;

	private String applicationMode;
	private String applicationLink;

	private Boolean requiredResumeUpload;
	private Boolean requiredCoverLetter;

	private Double salaryMin;
	private Double salaryMax;
	private Boolean salaryNegotiable;

	private String bonuses;

	@ElementCollection
	private List<String> perks;

	@Temporal(TemporalType.TIMESTAMP)
	private Date postedAt = new Date();
}
