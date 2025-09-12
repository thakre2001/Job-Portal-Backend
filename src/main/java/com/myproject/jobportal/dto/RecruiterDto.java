package com.myproject.jobportal.dto;
import lombok.Data;

@Data
public class RecruiterDto {
    private Long id;                
    private String name;            
    private String email;          
    private String mobile;          
    private String designation;
    private String alternateContact;
}

