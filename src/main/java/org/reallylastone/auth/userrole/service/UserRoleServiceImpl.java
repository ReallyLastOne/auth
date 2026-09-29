package org.reallylastone.auth.userrole.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.reallylastone.auth.role.domain.dto.SimpleRoleReadDto;
import org.reallylastone.auth.role.service.RoleService;
import org.reallylastone.auth.userrole.domain.dto.UserRolesReadDto;
import org.reallylastone.auth.userrole.domain.dto.UserRolesWriteDto;
import org.reallylastone.auth.userrole.domain.entity.UserRole;
import org.reallylastone.auth.userrole.repository.UserRoleRepository;
import org.reallylastone.common.i18n.Messages;
import org.reallylastone.common.model.GenericResponse;
import org.reallylastone.common.user.service.VUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final VUserService vUserService;
    private final RoleService roleService;
    private final Messages messages;

    @Override
    @Transactional
    public GenericResponse<Void> updateUserRoles(String userId, UserRolesWriteDto dto) {
        vUserService.requireExistsNotDeleted(userId);
        roleService.requireExistsNotDeleted(dto.roleIds());

        Instant now = Instant.now();
        userRoleRepository.deleteByUserId(userId, userId, now);

        List<UserRole> newAssignments = dto.roleIds().stream().map(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            return userRole;
        }).toList();
        userRoleRepository.saveAllAndFlush(newAssignments);

        return GenericResponse.success(messages.getMessage("user.role.update.success"));
    }

    @Override
    public UserRolesReadDto getUserRoles(String userId) {
        var user = vUserService.getById(userId);
        var roles = userRoleRepository.findRolesForUser(userId);

        var assigned = new ArrayList<SimpleRoleReadDto>();
        var unassigned = new ArrayList<SimpleRoleReadDto>();

        roles.forEach(role -> {
            var dto = new SimpleRoleReadDto(role.roleId(), role.name());

            (role.assigned() ? assigned : unassigned).add(dto);
        });

        return new UserRolesReadDto(userId, user.getUsername(), assigned, unassigned);
    }
}
