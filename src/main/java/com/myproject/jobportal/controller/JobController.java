package com.myproject.jobportal.controller;

import com.myproject.jobportal.dto.JobResponseDto;
import com.myproject.jobportal.entity.Job;
import com.myproject.jobportal.entity.JobStatus;
import com.myproject.jobportal.services.JobService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // Create a new Job (Recruiter posts a job)
    @PostMapping("/create")
    public ResponseEntity<JobResponseDto> createJob(@RequestBody Job job, Principal principal) {
        return ResponseEntity.ok(jobService.createJob(job, principal.getName()));
    }

    // Update Job
    @PutMapping("/{jobId}")
    public ResponseEntity<JobResponseDto> updateJob(@PathVariable Long jobId, @RequestBody Job job, Principal principal) {
        return ResponseEntity.ok(jobService.updateJob(jobId, job,principal.getName()));
    }
//
//    // Delete Job
    @DeleteMapping("/{jobId}")
    public ResponseEntity<String> deleteJob(@PathVariable Long jobId, Principal principal) {
        jobService.deleteJob(jobId,principal.getName());
        return ResponseEntity.ok("Job deleted successfully");
    }
//
//    // Get Job by ID
    @GetMapping("/{jobId}")
    public ResponseEntity<JobResponseDto> getJobById(@PathVariable Long jobId) {
        return ResponseEntity.ok(jobService.getJobById(jobId));
    }
//
//    // Get All Jobs
    @GetMapping("/all")	
    public ResponseEntity<List<JobResponseDto>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }
    
    @GetMapping("/allJobs/user")
    public List<JobResponseDto> getAllJobsForUser(Principal principal) {
		return jobService.getAllJobsForUser(principal.getName());
	}
    
//    // Get Jobs by Recruiter
    @GetMapping("/recruiter/my-jobs")
    public ResponseEntity<List<JobResponseDto>> getJobsByRecruiter(Principal principal) {
        return ResponseEntity.ok(jobService.getJobsByRecruiter(principal.getName()));
    }
    
    @PutMapping("/{jobId}/status")
    public void updateJobStatus(@PathVariable Long jobId, @RequestParam JobStatus status, Principal principal) {
		jobService.updateJobStatus(jobId,status,principal.getName());
	}
}

