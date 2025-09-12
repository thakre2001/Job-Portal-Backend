package com.myproject.jobportal.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class JobApplication {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Job job;
	
	@ManyToOne
	private User applicant;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "VARCHAR(50)")
	private ApplicationStatus status= ApplicationStatus.PENDING;
	
	private LocalDate appliedAt=LocalDate.now();
	
	@Lob
	private byte[] resume;

	public JobApplication() {
		super();
	}

	public JobApplication(Long id, Job job, User applicant, ApplicationStatus status, LocalDate appliedAt,
			byte[] resume) {
		super();
		this.id = id;
		this.job = job;
		this.applicant = applicant;
		this.status = status;
		this.appliedAt = appliedAt;
		this.resume = resume;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public User getApplicant() {
		return applicant;
	}

	public void setApplicant(User applicant) {
		this.applicant = applicant;
	}

	public ApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}

	public LocalDate getAppliedAt() {
		return appliedAt;
	}

	public void setAppliedAt(LocalDate appliedAt) {
		this.appliedAt = appliedAt;
	}

	public byte[] getResume() {
		return resume;
	}

	public void setResume(byte[] resume) {
		this.resume = resume;
	}

	
}
