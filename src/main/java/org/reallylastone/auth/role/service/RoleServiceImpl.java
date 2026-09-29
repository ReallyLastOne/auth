package org.reallylastone.auth.role.service;

import org.reallylastone.auth.role.domain.dto.RoleReadDto;
import org.reallylastone.auth.role.domain.dto.RoleWriteDto;
import org.reallylastone.auth.role.domain.entity.Role;
import org.reallylastone.auth.role.domain.entity.VRoleList;
import org.reallylastone.auth.role.repository.RoleRepository;
import org.reallylastone.auth.role.repository.VRoleListRepository;
import org.reallylastone.auth.userrole.repository.UserRoleRepository;
import org.reallylastone.auth.utils.Permissions;
import org.reallylastone.common.exception.BadRequestException;
import org.reallylastone.common.i18n.Messages;
import org.reallylastone.common.model.GenericResponse;
import org.reallylastone.common.page.model.DataPage;
import org.reallylastone.common.page.query.QueryPageRequest;
import org.reallylastone.common.page.service.PageService;
import org.reallylastone.common.service.DeletableMessages;
import org.reallylastone.common.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {
    @Getter
    private final RoleRepository repository;
    private final Messages messages;
    private final PageService pageService;
    private final VRoleListRepository vRoleListRepository;
    private final RoleMapper roleMapper;
    private final UserRoleRepository userRoleRepository;

    @Override
    public DataPage<VRoleList> getRoles(QueryPageRequest request) {
        boolean canModify = UserUtils.hasAuthority(Permissions.ROLES_MODIFY_ALL);

        return pageService.getPage(request, vRoleListRepository, c -> {
            c.setCanDelete(c.isCanDelete() && canModify);
            c.setCanEdit(c.isCanEdit() && canModify);
        });
    }

    @Override
    public RoleReadDto getRole(Long roleId) {
        return roleMapper.toDto(getNotDeletedById(roleId));
    }

    @Override
    @Transactional
    public GenericResponse<Void> createRole(RoleWriteDto dto) {
        if (repository.existsByLowerName(dto.name(), null)) {
            throw new BadRequestException(messages.getMessage("role.name.taken"));
        }

        Role entity = roleMapper.toEntity(new Role(), dto);
        var saved = repository.save(entity);

        return GenericResponse.successWithId(messages.getMessage("role.created"),
                saved.getRoleId());
    }

    @Override
    @Transactional
    public GenericResponse<Void> updateRole(Long roleId, RoleWriteDto dto) {
        var role = getNotDeletedById(roleId, dto.version());
        requireNotSystem(role);

        if (repository.existsByLowerName(dto.name(), roleId)) {
            throw new BadRequestException(messages.getMessage("role.name.taken"));
        }
        roleMapper.toEntity(role, dto);

        return GenericResponse.successWithId(messages.getMessage("role.updated"), roleId);
    }

    @Override
    @Transactional
    public GenericResponse<Void> deleteRole(Long roleId) {
        var role = getNotDeletedById(roleId);
        requireNotSystem(role);

        if (userRoleRepository.existsByRoleIdAndDeletedFalse(roleId)) {
            throw new BadRequestException(messages.getMessage("role.in.use"));
        }

        markAsDeleted(roleId);
        return GenericResponse.successWithId(messages.getMessage("role.deleted"), roleId);
    }

    @Override
    public DeletableMessages getDeletableMessages() {
        return DeletableMessages.of("role.not.found");
    }

    private void requireNotSystem(Role role) {
        if (role.isSystem()) {
            throw new BadRequestException(messages.getMessage("role.system.forbidden"));
        }
    }
}
