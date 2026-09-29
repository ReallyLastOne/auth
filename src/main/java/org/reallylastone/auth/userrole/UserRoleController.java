package org.reallylastone.auth.userrole;

import static org.reallylastone.common.utils.Constants.AUTH_API_PREFIX;

import org.reallylastone.auth.userrole.domain.dto.UserRolesReadDto;
import org.reallylastone.auth.userrole.domain.dto.UserRolesWriteDto;
import org.reallylastone.auth.userrole.service.UserRoleService;
import org.reallylastone.auth.utils.Permissions;
import org.reallylastone.common.model.GenericResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RequestMapping(AUTH_API_PREFIX + "/user-roles")
@RestController
@Tag(name = "User Roles")
public class UserRoleController {

    private final UserRoleService userRoleService;

    @Operation(summary = "Get given user assigned roles and roles available to assign")
    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('" + Permissions.USER_ROLES_READ_ALL + "')")
    public UserRolesReadDto getUserRoles(@PathVariable String userId) {
        return userRoleService.getUserRoles(userId);
    }

    @Operation(summary = "Replace the roles to the given user")
    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('" + Permissions.USER_ROLES_MODIFY_ALL + "')")
    public GenericResponse<Void> updateUserRoles(@PathVariable String userId,
            @RequestBody @Valid UserRolesWriteDto dto) {
        return userRoleService.updateUserRoles(userId, dto);
    }
}
