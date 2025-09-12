package com.myproject.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myproject.jobportal.entity.Job;
import com.myproject.jobportal.entity.JobApplication;
import com.myproject.jobportal.entity.User;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long>{

	List<JobApplication> findByApplicant(User user);

	List<JobApplication> findByJob(Job job);

	@Query("SELECT ja.job.id FROM JobApplication ja WHERE ja.applicant.id= :userId")
	List<Long> findJobIdsByUser(@Param("userId") Long userId);



}
