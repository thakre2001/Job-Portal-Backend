package com.myproject.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.jobportal.entity.Skills;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, Long>{

}
