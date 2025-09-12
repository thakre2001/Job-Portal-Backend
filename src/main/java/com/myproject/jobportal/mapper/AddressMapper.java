package com.myproject.jobportal.mapper;

import com.myproject.jobportal.dto.AddressDTO;
import com.myproject.jobportal.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDTO toDto(Address address);
    Address toEntity(AddressDTO dto);
}
