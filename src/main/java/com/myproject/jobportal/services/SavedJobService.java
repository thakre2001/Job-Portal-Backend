package com.myproject.jobportal.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.myproject.jobportal.dto.SavedJobResponseDto;
import com.myproject.jobportal.entity.Job;
import com.myproject.jobportal.entity.SavedJob;
import com.myproject.jobportal.entity.User;
import com.myproject.jobportal.mapper.SavedJobMapper;
import com.myproject.jobportal.repository.JobApplicationRepository;
import com.myproject.jobportal.repository.JobRepository;
import com.myproject.jobportal.repository.SavedJobRepository;
import com.myproject.jobportal.repository.UserRepository;

@Service
public class SavedJobService {

	@Autowired
	private SavedJobRepository savedjobRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private JobApplicationRepository applicationRepository;
	
	@Autowired
	private SavedJobMapper savedJobMapper;

	public SavedJobResponseDto saveJob(Long jobId, String name) {
		User user = userRepository.findByEmail(name)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		Job job = jobRepository.findById(jobId)
				.orElseThrow(() -> new RuntimeException("Job not found"));
		
		List<Long> jobIdsByUser = applicationRepository.findJobIdsByUser(user.getId());
		
		if(jobIdsByUser.contains(job.getJobId())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job is already applied");
		}
		
		Optional<SavedJob> existing = savedjobRepository.findByUserAndJob(user, job);
		
		if(existing.isPresent()) {
			throw new RuntimeException("Job already saved");
		}
		
		SavedJob savedJob=new SavedJob();
		
		savedJob.setUser(user);
		savedJob.setJob(job);
		
		SavedJob saved = savedjobRepository.save(savedJob);
		
		SavedJobResponseDto dto=new SavedJobResponseDto();
		
		dto.setId(saved.getId());
		dto.setJobId(saved.getJob().getJobId());
		dto.setCompanyName(saved.getJob().getCompany().getCompanyName());
		dto.setEmploymentType(saved.getJob().getEmploymentType());
		dto.setJobTitle(saved.getJob().getJobTitle());	
		dto.setLocation(saved.getJob().getJobLocations());
		dto.setSavedAt(saved.getSavedAt());
		
		return dto;

	}


	public ResponseEntity<?> removeSavedJob(Long jobId, String name){
		User user = userRepository.findByEmail(name)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		Job job = jobRepository.findById(jobId)
				.orElseThrow(()-> new RuntimeException("job not found"));
		
		SavedJob savedJob=savedjobRepository.findByUserAndJob(user,job)
				.orElseThrow(() -> new RuntimeException("Saved job not found"));
		
		savedjobRepository.delete(savedJob);
		
		Map<String, Object> response=new HashMap<>();
		response.put("status", "success");
		response.put("message", "Job unsaved successfully");
		response.put("jobId", jobId);
		
		return ResponseEntity.ok(response);
	}
	

	public List<SavedJobResponseDto> getSavedJobsByUser(String name) {
		User user = userRepository.findByEmail(name)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		List<SavedJob> savedJobs = savedjobRepository.findByUser(user);
		
		return savedJobs.stream().map(savedJobMapper::toDto).toList();
		
	}

}
