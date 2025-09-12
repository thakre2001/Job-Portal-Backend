package com.myproject.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.jobportal.entity.Job;
import com.myproject.jobportal.entity.User;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

	List<Job> findByPostedBy(User recruiter);

}

