package com.myproject.jobportal.services;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myproject.jobportal.dto.JobResponseDto;
import com.myproject.jobportal.entity.ApplicationStatus;
import com.myproject.jobportal.entity.Job;
import com.myproject.jobportal.entity.JobApplication;
import com.myproject.jobportal.entity.JobStatus;
import com.myproject.jobportal.entity.User;
import com.myproject.jobportal.entity.UserRole;
import com.myproject.jobportal.mapper.JobMapper;
import com.myproject.jobportal.repository.JobApplicationRepository;
import com.myproject.jobportal.repository.JobRepository;
import com.myproject.jobportal.repository.UserRepository;

@Service
public class JobService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private JobApplicationRepository applicationRepository;

	@Autowired
	private JobMapper jobMapper;

	public JobResponseDto createJob(Job job, String email) {
		User recruiter = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		if (!recruiter.getRole().equals(UserRole.RECRUITER)) {
			throw new RuntimeException("Only recruiters can post jobs");
		}

		job.setPostedBy(recruiter);
		job.setCompany(recruiter.getCompany());
		job.setStatus(JobStatus.DRAFT);
		Job savedJob = jobRepository.save(job);

		return jobMapper.toDto(savedJob);
	}

	public Object updateJob(Long jobId, Job job) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<JobResponseDto> getAllJobs() {
		List<Job> jobs = jobRepository.findAll();

		return jobs.stream().map(jobMapper::toDto).toList();
	}
	
	public List<JobResponseDto> getAllJobsForUser(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		List<Job> jobs = jobRepository.findAll();
		
		List<Long> appliedJobIds = applicationRepository.findJobIdsByUser(user.getId());
		
		List<JobResponseDto> dtos = jobMapper.toDtoList(jobs);
		
		dtos.forEach(dto -> dto.setAlreadyApplied(appliedJobIds.contains(dto.getJobId())));
		
		return dtos;
		
	}

	public List<JobResponseDto> getJobsByRecruiter(String email) {
		User recruiter = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		if (!recruiter.getRole().equals(UserRole.RECRUITER)) {
			throw new RuntimeException("Only recruiters can get jobs");
		}

		List<Job> recruiterJobs = jobRepository.findByPostedBy(recruiter);

		return recruiterJobs.stream().map(jobMapper::toDto).toList();
	}

	public JobResponseDto updateJob(Long jobId, Job updatedJob, String email) {
		User recruiter = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		Job existingJob = jobRepository.findById(jobId)
				.orElseThrow(() -> new RuntimeException("Job not found with id: " + jobId));

		if (!existingJob.getPostedBy().getId().equals(recruiter.getId())) {
			throw new RuntimeException("You are not authorized to update this job");
		}

		existingJob.setJobTitle(updatedJob.getJobTitle());
		existingJob.setJobDescription(updatedJob.getJobDescription());
		existingJob.setJobLocations(updatedJob.getJobLocations());
		existingJob.setEmploymentType(updatedJob.getEmploymentType());
		existingJob.setJobCategory(updatedJob.getJobCategory());
		existingJob.setJobRole(updatedJob.getJobRole());
		existingJob.setIndustryType(updatedJob.getIndustryType());
		existingJob.setWorkMode(updatedJob.getWorkMode());
		existingJob.setNoOfOpenings(updatedJob.getNoOfOpenings());
		existingJob.setShiftTime(updatedJob.getShiftTime());
		existingJob.setJoiningDate(updatedJob.getJoiningDate());
		existingJob.setApplicationDeadline(updatedJob.getApplicationDeadline());
		existingJob.setEducationMin(updatedJob.getEducationMin());
		existingJob.setPreferredQualification(updatedJob.getPreferredQualification());
		existingJob.setExperienceMin(updatedJob.getExperienceMin());
		existingJob.setExperienceMax(updatedJob.getExperienceMax());
		existingJob.setSkills(updatedJob.getSkills());
		existingJob.setLanguageKnown(updatedJob.getLanguageKnown());
		existingJob.setNoticePeriod(updatedJob.getNoticePeriod());
		existingJob.setCertificationRequired(updatedJob.getCertificationRequired());
		existingJob.setApplicationMode(updatedJob.getApplicationMode());
		existingJob.setApplicationLink(updatedJob.getApplicationLink());
		existingJob.setRequiredResumeUpload(updatedJob.getRequiredResumeUpload());
		existingJob.setRequiredCoverLetter(updatedJob.getRequiredCoverLetter());
		existingJob.setSalaryMin(updatedJob.getSalaryMin());
		existingJob.setSalaryMax(updatedJob.getSalaryMax());
		existingJob.setSalaryNegotiable(updatedJob.getSalaryNegotiable());
		existingJob.setBonuses(updatedJob.getBonuses());
		existingJob.setPerks(updatedJob.getPerks());
		existingJob.setStatus(updatedJob.getStatus());

		Job savedJob = jobRepository.save(existingJob);

		return jobMapper.toDto(savedJob);
	}

	public JobResponseDto getJobById(Long jobId) {
		Job job = jobRepository.findById(jobId)
				.orElseThrow(() -> new RuntimeException("Job not found with id: " + jobId));

		return jobMapper.toDto(job);
	}

	public void deleteJob(Long jobId, String email) {
		User recruiter = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		Job existingJob = jobRepository.findById(jobId)
				.orElseThrow(() -> new RuntimeException("Job not found with id: " + jobId));

		if (!existingJob.getPostedBy().getId().equals(recruiter.getId())) {
			throw new RuntimeException("You are not authorized to delete this job");
		}

		jobRepository.delete(existingJob);
	}

	public void updateJobStatus(Long jobId, JobStatus status, String email) {
		User recruiter = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		Job existingJob = jobRepository.findById(jobId)
				.orElseThrow(() -> new RuntimeException("Job not found with id: " + jobId));

		if (!existingJob.getPostedBy().getId().equals(recruiter.getId())) {
			throw new RuntimeException("You are not authorized to change status of this job");
		}

		existingJob.setStatus(status);

		jobRepository.save(existingJob);

		List<JobApplication> applications = applicationRepository.findByJob(existingJob);

		if (existingJob.getStatus().equals(JobStatus.CLOSED)) {
			for (JobApplication jobApplication : applications) {
				jobApplication.setStatus(ApplicationStatus.REJECTED);
			}
			applicationRepository.saveAll(applications);
		}

	}

}
