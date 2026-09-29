package org.reallylastone.auth.user.service;

import org.reallylastone.auth.user.domain.entity.VUserList;
import org.reallylastone.common.page.model.DataPage;
import org.reallylastone.common.page.query.QueryPageRequest;
import org.reallylastone.common.user.domain.dto.UserInfoDto;

public interface UserService {

    UserInfoDto getCurrent();

    DataPage<VUserList> getUsers(QueryPageRequest request);
}
