package com.myproject.jobportal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.jobportal.entity.Job;
import com.myproject.jobportal.entity.SavedJob;
import com.myproject.jobportal.entity.User;

public interface SavedJobRepository extends JpaRepository<SavedJob, Long> {

	List<SavedJob> findByUser(User user);

	Optional<SavedJob> findByUserAndJob(User user, Job job);

}
