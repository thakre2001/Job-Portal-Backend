package com.myproject.jobportal.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private String name;
    private String email;
    private String mobile;
    private String workStatus;
    private byte[] profilePhoto;
}

