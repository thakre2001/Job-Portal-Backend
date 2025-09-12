package com.myproject.jobportal.entity;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String companyName;
	private String companyWebsite;
	private String industryType;
	private String companySize;
	private String companyDescription;
	private String companyLogo;
	private String gstNumber;
	private String panNumber;
	private String linkedinProfile;
	private String hiringPlan;

	@Embedded
	private Address address;

	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
	private List<User> recruiters;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by_id")
	private User createdBy;
}
