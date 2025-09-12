package com.myproject.jobportal.services;

import com.myproject.jobportal.entity.Education;
import com.myproject.jobportal.entity.User;
import com.myproject.jobportal.repository.EducationRepository;
import com.myproject.jobportal.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationService {

    private final EducationRepository repository;
    private final UserRepository userRepository;

    public EducationService(EducationRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    // ✅ Add education for logged-in user
    public Education addEducation(Education education, String email) {
        User user = getUserByEmail(email);
        education.setUser(user);
        return repository.save(education);
    }

    // ✅ Get all educations of logged-in user
    public List<Education> getAllEducations(String email) {
        User user = getUserByEmail(email);
        return repository.findByUser(user);
    }

    // ✅ Get one education by ID for logged-in user
    public Education getEducationById(Long id, String email) {
        User user = getUserByEmail(email);
        return repository.findByIdAndUser(id, user).orElseThrow(() -> new RuntimeException("Education not found or not owned by user"));
    }

    // ✅ Update education only if it belongs to logged-in user
    public Education updateEducation(Long id, Education updatedEducation, String email) {
        User user = getUserByEmail(email);
        Education existing = repository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Education not found or not owned by user"));

        existing.setEducationType(updatedEducation.getEducationType());
        existing.setOrganization(updatedEducation.getOrganization());
        existing.setStartYear(updatedEducation.getStartYear());
        existing.setEndYear(updatedEducation.getEndYear());
        existing.setPercentage(updatedEducation.getPercentage());

        return repository.save(existing);
    }

    // ✅ Delete only if education belongs to logged-in user
    public void deleteEducation(Long id, String email) {
        User user = getUserByEmail(email);
        Education education = repository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Education not found or not owned by user"));

        repository.delete(education);
    }
}
