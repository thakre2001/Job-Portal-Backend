package com.myproject.jobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EducationResponseDTO {

    private Long id;
    private String educationType;
    private String organization;
    private Integer startYear;
    private Integer endYear;
    private Double percentage;
}
