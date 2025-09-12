package com.myproject.jobportal.controller;

import com.myproject.jobportal.entity.Education;
import com.myproject.jobportal.services.EducationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/education")
public class EducationController {

    private final EducationService service;

    public EducationController(EducationService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<Education> addEducation(@RequestBody Education education, Principal principal) {
        return ResponseEntity.ok(service.addEducation(education,principal.getName()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Education>> getAllEducations(Principal principal) {
        return ResponseEntity.ok(service.getAllEducations(principal.getName()));
    }

    @GetMapping("/{id}")
    public Education getEducationById(@PathVariable Long id,Principal principal) {
        return service.getEducationById(id,principal.getName());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Education> updateEducation(@PathVariable Long id, @RequestBody Education education, Principal principal) {
        return ResponseEntity.ok(service.updateEducation(id, education,principal.getName()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEducation(@PathVariable Long id,Principal principal) {
        service.deleteEducation(id,principal.getName());
        return ResponseEntity.noContent().build();
    }
}
