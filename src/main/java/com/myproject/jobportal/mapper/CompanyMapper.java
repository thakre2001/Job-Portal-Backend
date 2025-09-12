package com.myproject.jobportal.mapper;

import com.myproject.jobportal.dto.CompanyDTO;
import com.myproject.jobportal.entity.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { AddressMapper.class })
public interface CompanyMapper {
    CompanyDTO toDto(Company company);
    Company toEntity(CompanyDTO dto);
}
