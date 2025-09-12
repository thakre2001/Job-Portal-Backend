package com.myproject.jobportal.services;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.myproject.jobportal.dto.ApplicationStatusRequestDto;
import com.myproject.jobportal.dto.JobApplicationRequestDTO;
import com.myproject.jobportal.dto.JobApplicationResponseDTO;
import com.myproject.jobportal.entity.ApplicationStatus;
import com.myproject.jobportal.entity.Job;
import com.myproject.jobportal.entity.JobApplication;
import com.myproject.jobportal.entity.JobStatus;
import com.myproject.jobportal.entity.User;
import com.myproject.jobportal.entity.UserRole;
import com.myproject.jobportal.mapper.JobApplicationMapper;
import com.myproject.jobportal.repository.JobApplicationRepository;
import com.myproject.jobportal.repository.JobRepository;
import com.myproject.jobportal.repository.UserRepository;



import jakarta.mail.Multipart;

@Service
public class JobApplicationService {

	@Autowired
	private JobApplicationRepository applicationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private JobApplicationMapper applicationMapper;
	
	@Autowired
	private FileStorageService fileStorageService;
	
	public ResponseEntity<String> applyForJob(Long jobId,MultipartFile resume,String coverLetter , String name) throws IOException {
		User user = userRepository.findByEmail(name).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		Job job=jobRepository.findById(jobId).orElseThrow(()-> new UsernameNotFoundException("Job not found"));
		
		if(resume.getSize()>2 *1024 * 1024) {
			return ResponseEntity.badRequest().body("File size is too large");
		}
		
		String contentType=resume.getContentType();
		if(!(contentType.equals("application/pdf") ||
		          contentType.equals("application/msword") ||
		          contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
				)) {
			return ResponseEntity.badRequest().body("Only PDF, DOC, DOCX files are allowed");
		}
		
		JobApplication application=new JobApplication();
		application.setApplicant(user);
		application.setJob(job);
		application.setStatus(ApplicationStatus.PENDING);
		application.setResume(resume.getBytes());
		application.setAppliedAt(LocalDate.now());
		
		JobApplication jobApplication = applicationRepository.save(application);
		
		job.setApplicationCount(job.getApplicationCount()+1);
		
		jobRepository.save(job);
		
		return ResponseEntity.ok("Application submitted successfully");
		
	}

	public JobApplication getApplicationById(Long id, String name) {
		User user = userRepository.findByEmail(name).orElseThrow(() -> new UsernameNotFoundException("User not found"));

		JobApplication jobApplication = applicationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Application not found"));

		if (user.getRole().equals(UserRole.RECRUITER)) {
			if (!jobApplication.getJob().getPostedBy().getId().equals(user.getId())) {
				throw new RuntimeException("You are not allowed to view this application");
			}
		} else if (user.getRole().equals(UserRole.USER)) {
			if (!jobApplication.getApplicant().getId().equals(user.getId())) {
				throw new RuntimeException("You are not allowed to view this application");
			}
		} else if (!user.getRole().equals(UserRole.ADMIN)) {
			throw new RuntimeException("Unauthorized");
		}

		return jobApplication;

	}

	public List<JobApplicationResponseDTO> getApplicantsJobApplications(String name) {
		User user = userRepository.findByEmail(name).orElseThrow(() -> new UsernameNotFoundException("User not found"));

		 List<JobApplication> applications = applicationRepository.findByApplicant(user);
		 
		 return applications
				.stream()
				.map(applicationMapper::toDto)
				.toList();
		
	}

	public JobApplication updateStatus(Long id, ApplicationStatusRequestDto dto, String name) {
		User user = userRepository.findByEmail(name)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		JobApplication jobApplication = applicationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Application not found"));
		
		if(!jobApplication.getJob().getPostedBy().getId().equals(user.getId())) {
			throw new AccessDeniedException("You are not allowed to change status of this application");
		}
		
		if(jobApplication.getJob().getStatus().equals(JobStatus.CLOSED)) {
			throw new IllegalStateException("Job is closed, you can't change status");
		}
		
		try {
			jobApplication.setStatus(ApplicationStatus.valueOf(dto.getStatus().toUpperCase()));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid status value: " + dto.getStatus());
		}
		
		return applicationRepository.save(jobApplication);
	}

	public List<JobApplicationResponseDTO> getJobApplicationsForRecruiter(Long jobId,String name) {
		User user = userRepository.findByEmail(name)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		if(!user.getRole().equals(UserRole.RECRUITER)) {
			throw new RuntimeException("You are not allowed to see job applications for this job");
		}
		Job job=jobRepository.findById(jobId).orElseThrow(()-> new UsernameNotFoundException("Job not found"));
		
		if(!job.getPostedBy().getId().equals(user.getId())) {
			throw new RuntimeException("You are not allowed to see applications for this job");
		}
		
		List<JobApplication> jobApplications = applicationRepository.findByJob(job);
		
		return jobApplications.stream().map(applicationMapper::toDto).toList();
		
	}

	public ResponseEntity<byte[]> downloadResume(Long applicationId, Principal authentication) {
        JobApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

            User recruiter = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("Recruiter not found"));

            if (!application.getJob().getPostedBy().getId().equals(recruiter.getId())) {
                throw new AccessDeniedException("You are not authorized to view this resume");
            }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=resume.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(application.getResume());
    }

}
