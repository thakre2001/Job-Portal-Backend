package com.myproject.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.jobportal.entity.Experience;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long>{

}
