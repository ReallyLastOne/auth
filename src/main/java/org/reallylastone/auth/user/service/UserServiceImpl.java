package org.reallylastone.auth.user.service;

import org.reallylastone.auth.user.domain.entity.VUserList;
import org.reallylastone.auth.user.repository.VUserListRepository;
import org.reallylastone.auth.utils.Permissions;
import org.reallylastone.common.page.model.DataPage;
import org.reallylastone.common.page.query.QueryPageRequest;
import org.reallylastone.common.page.service.PageService;
import org.reallylastone.common.user.domain.dto.UserInfoDto;
import org.reallylastone.common.utils.UserUtils;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final PageService pageService;
    private final VUserListRepository vUserListRepository;

    @Override
    public UserInfoDto getCurrent() {
        return UserUtils.getCurrent();
    }

    @Override
    public DataPage<VUserList> getUsers(QueryPageRequest request) {
        boolean canEditUserRoles = UserUtils.hasAuthority(Permissions.USER_ROLES_MODIFY_ALL);
        boolean canEdit = UserUtils.hasAuthority(Permissions.USERS_MODIFY_ALL);

        return pageService.getPage(request, vUserListRepository, c -> {
            c.setCanEditUserRoles(canEditUserRoles);
            c.setCanEdit(canEdit);
        });
    }
}
