package com.myproject.jobportal.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.myproject.jobportal.dto.SkillDto;
import com.myproject.jobportal.dto.UserResponseDto;
import com.myproject.jobportal.entity.Skills;
import com.myproject.jobportal.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "skills", source = "skills", qualifiedByName = "mapSkillsToSkillDtos")
    UserResponseDto userToUserResponseDto(User user);

    @Named("mapSkillsToSkillDtos")
    default List<SkillDto> mapSkillsToSkillDtos(List<Skills> skills) {
        if (skills == null) return null;
        return skills.stream().map(skill -> {
            SkillDto dto = new SkillDto();
            dto.setId(skill.getId());
            dto.setName(skill.getName());
            return dto;
        }).collect(Collectors.toList());
    }
}
