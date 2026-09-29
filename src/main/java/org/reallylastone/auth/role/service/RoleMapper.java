package org.reallylastone.auth.role.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.reallylastone.auth.role.domain.dto.RoleReadDto;
import org.reallylastone.auth.role.domain.dto.RoleWriteDto;
import org.reallylastone.auth.role.domain.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toEntity(@MappingTarget Role role, RoleWriteDto dto);

    @Mapping(target = "version", ignore = true)
    RoleReadDto toDto(Role role);
}
