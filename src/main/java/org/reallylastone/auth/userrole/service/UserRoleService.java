package org.reallylastone.auth.userrole.service;

import org.reallylastone.auth.userrole.domain.dto.UserRolesReadDto;
import org.reallylastone.auth.userrole.domain.dto.UserRolesWriteDto;
import org.reallylastone.common.model.GenericResponse;

public interface UserRoleService {

    GenericResponse<Void> updateUserRoles(String userId, UserRolesWriteDto dto);

    UserRolesReadDto getUserRoles(String userId);
}
