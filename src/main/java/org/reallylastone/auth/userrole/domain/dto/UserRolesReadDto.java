package org.reallylastone.auth.userrole.domain.dto;

import java.util.List;

import org.reallylastone.auth.role.domain.dto.SimpleRoleReadDto;


public record UserRolesReadDto(String userId, String username,
        List<SimpleRoleReadDto> assignedRoles, List<SimpleRoleReadDto> availableRoles) {
}
