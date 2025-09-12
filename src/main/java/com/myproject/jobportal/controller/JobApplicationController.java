package com.myproject.jobportal.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.myproject.jobportal.dto.ApplicationStatusRequestDto;
import com.myproject.jobportal.dto.JobApplicationRequestDTO;
import com.myproject.jobportal.dto.JobApplicationResponseDTO;
import com.myproject.jobportal.entity.JobApplication;
import com.myproject.jobportal.entity.User;
import com.myproject.jobportal.repository.JobApplicationRepository;
import com.myproject.jobportal.services.JobApplicationService;

import jakarta.mail.Multipart;

@RestController
@RequestMapping("/job-application")
public class JobApplicationController {

	@Autowired
	private JobApplicationService applicationService;

	private JobApplicationRepository applicationRepository;

	@PostMapping("/apply-job/{jobId}")
	public ResponseEntity<String> applyForJob(@PathVariable Long jobId, @RequestParam("resume") MultipartFile resume,
			@RequestParam(value = "coverLetter") String coverLetter, Principal principal) throws IOException {
		return applicationService.applyForJob(jobId, resume, coverLetter, principal.getName());
	}

	@GetMapping("/get-application/{id}")
	public void getApplicationById(@PathVariable Long id, Principal principal) {
		applicationService.getApplicationById(id, principal.getName());
	}

	@GetMapping("/application")
	public ResponseEntity<List<JobApplicationResponseDTO>> getApplicantsJobApplications(Principal principal) {
		List<JobApplicationResponseDTO> applications = applicationService
				.getApplicantsJobApplications(principal.getName());

		return ResponseEntity.ok(applications);
	}

	@PutMapping("/status/{id}")
	public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestBody ApplicationStatusRequestDto dto,
			Principal principal) {
		JobApplication updateStatus = applicationService.updateStatus(id, dto, principal.getName());

		return ResponseEntity.ok("Status updated successfully");
	}

	@GetMapping("/recruiter-jobApplications/{jobId}")
	public List<JobApplicationResponseDTO> getJobApplicationsForRecruiter(@PathVariable Long jobId,
			Principal principal) {
		return applicationService.getJobApplicationsForRecruiter(jobId, principal.getName());
	}

	@GetMapping("/{id}/resume")
	public ResponseEntity<byte[]> downloadResume(@PathVariable Long id, Principal authentication) {
		return applicationService.downloadResume(id, authentication);
	}

}
