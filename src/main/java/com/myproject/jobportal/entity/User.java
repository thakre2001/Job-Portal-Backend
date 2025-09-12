package com.myproject.jobportal.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Column(unique = true, nullable = false)
	private String email;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role;

	@Lob
	@Column(name = "profile_photo", columnDefinition = "LONGBLOB")
	private byte[] profilePhoto;

	private String password;

	@Column(name = "reset_otp")
	private String resetOtp;

	@Column(name = "reset_otp_expiry")
	private LocalDateTime resetOtpExpiry;

	private String mobile;

	private String workStatus;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_skills", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
	private List<Skills> skills;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Education> education = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Experience> experience = new ArrayList<>();

	@Lob
	private byte[] resume;

//	recruiter's additional data

	private String recruiterDesignation;

	private String alternateContact;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	public User() {
		super();
	}

	public User(Long id, String name, String email, UserRole role, byte[] profilePhoto, String password,
			String resetOtp, LocalDateTime resetOtpExpiry, String mobile, String workStatus, List<Skills> skills,
			List<Education> education, List<Experience> experience, byte[] resume, String recruiterDesignation,
			String alternateContact, Company company) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
		this.profilePhoto = profilePhoto;
		this.password = password;
		this.resetOtp = resetOtp;
		this.resetOtpExpiry = resetOtpExpiry;
		this.mobile = mobile;
		this.workStatus = workStatus;
		this.skills = skills;
		this.education = education;
		this.experience = experience;
		this.resume = resume;
		this.recruiterDesignation = recruiterDesignation;
		this.alternateContact = alternateContact;
		this.company = company;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public byte[] getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(byte[] profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getResetOtp() {
		return resetOtp;
	}

	public void setResetOtp(String resetOtp) {
		this.resetOtp = resetOtp;
	}

	public LocalDateTime getResetOtpExpiry() {
		return resetOtpExpiry;
	}

	public void setResetOtpExpiry(LocalDateTime resetOtpExpiry) {
		this.resetOtpExpiry = resetOtpExpiry;
	}

	public List<Experience> getExperience() {
		return experience;
	}

	public void setExperience(List<Experience> experience) {
		this.experience = experience;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public List<Skills> getSkills() {
		return skills;
	}

	public void setSkills(List<Skills> skills) {
		this.skills = skills;
	}

	public List<Education> getEducation() {
		return education;
	}

	public void setEducation(List<Education> education) {
		this.education = education;
	}

	public byte[] getResume() {
		return resume;
	}

	public void setResume(byte[] resume) {
		this.resume = resume;
	}

	public String getRecruiterDesignation() {
		return recruiterDesignation;
	}

	public void setRecruiterDesignation(String recruiterDesignation) {
		this.recruiterDesignation = recruiterDesignation;
	}

	public String getAlternateContact() {
		return alternateContact;
	}

	public void setAlternateContact(String alternateContact) {
		this.alternateContact = alternateContact;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	

}
