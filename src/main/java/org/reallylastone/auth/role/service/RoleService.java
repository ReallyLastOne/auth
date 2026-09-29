package org.reallylastone.auth.role.service;

import org.reallylastone.auth.role.domain.dto.RoleReadDto;
import org.reallylastone.auth.role.domain.dto.SimpleRoleReadDto;
import org.reallylastone.auth.role.domain.dto.RoleWriteDto;
import org.reallylastone.auth.role.domain.entity.Role;
import org.reallylastone.auth.role.domain.entity.VRoleList;
import org.reallylastone.auth.role.repository.RoleRepository;
import org.reallylastone.common.model.GenericResponse;
import org.reallylastone.common.page.model.DataPage;
import org.reallylastone.common.page.query.QueryPageRequest;
import org.reallylastone.common.service.AuditableService;

public interface RoleService extends AuditableService<Role, Long, RoleRepository> {
    DataPage<VRoleList> getRoles(QueryPageRequest request);

    RoleReadDto getRole(Long roleId);

    GenericResponse<Void> createRole(RoleWriteDto request);

    GenericResponse<Void> updateRole(Long roleId, RoleWriteDto request);

    GenericResponse<Void> deleteRole(Long roleId);
}
