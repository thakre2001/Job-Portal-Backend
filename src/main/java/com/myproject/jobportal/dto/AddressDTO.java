package com.myproject.jobportal.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private String country;
    private String state;
    private String city;
    private String streetAddress;
    private String postalCode;
}
