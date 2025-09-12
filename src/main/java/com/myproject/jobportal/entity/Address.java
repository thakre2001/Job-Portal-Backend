package com.myproject.jobportal.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Address {
    private String country;
    private String state;
    private String city;
    private String streetAddress;
    private String postalCode;
}
