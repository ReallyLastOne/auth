package org.reallylastone.auth.role;

import static org.reallylastone.common.utils.Constants.AUTH_API_PREFIX;

import org.reallylastone.auth.role.domain.dto.RoleReadDto;
import org.reallylastone.auth.role.domain.dto.RoleWriteDto;
import org.reallylastone.auth.role.domain.entity.VRoleList;
import org.reallylastone.auth.role.service.RoleService;
import org.reallylastone.auth.utils.Permissions;
import org.reallylastone.common.model.GenericResponse;
import org.reallylastone.common.page.model.DataPage;
import org.reallylastone.common.page.query.QueryPageRequest;
import org.reallylastone.common.page.query.QueryRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(AUTH_API_PREFIX + "/roles")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    @Operation(summary = "Returns a paginated list of roles")
    @PreAuthorize("hasAuthority('" + Permissions.ROLES_READ_ALL + "')")
    public DataPage<VRoleList> getRoles(@QueryRequest @Valid QueryPageRequest request) {
        return roleService.getRoles(request);
    }

    @GetMapping("/{roleId}")
    @Operation(summary = "Get role by ID")
    @PreAuthorize("hasAuthority('" + Permissions.ROLES_READ_ALL + "')")
    public RoleReadDto getRole(@PathVariable Long roleId) {
        return roleService.getRole(roleId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create role")
    @PreAuthorize("hasAuthority('" + Permissions.ROLES_MODIFY_ALL + "')")
    public GenericResponse<Void> createRole(@RequestBody @Valid RoleWriteDto request) {
        return roleService.createRole(request);
    }

    @PutMapping("/{roleId}")
    @Operation(summary = "Update role")
    @PreAuthorize("hasAuthority('" + Permissions.ROLES_MODIFY_ALL + "')")
    public GenericResponse<Void> updateRole(@PathVariable Long roleId,
            @RequestBody @Valid RoleWriteDto request) {
        return roleService.updateRole(roleId, request);
    }

    @DeleteMapping("/{roleId}")
    @Operation(summary = "Delete role")
    @PreAuthorize("hasAuthority('" + Permissions.ROLES_MODIFY_ALL + "')")
    public GenericResponse<Void> deleteRole(@PathVariable Long roleId) {
        return roleService.deleteRole(roleId);
    }
}
