package com.myproject.jobportal.repository;

import com.myproject.jobportal.entity.Education;
import com.myproject.jobportal.entity.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long> {

	Optional<Education> findByIdAndUser(Long id, User user);

	List<Education> findByUser(User user);

}
