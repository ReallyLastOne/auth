package org.reallylastone.auth.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Permissions {
    public static final String USERS_READ_ALL = "users:read:all";
    public static final String USERS_MODIFY_ALL = "users:modify:all";
    public static final String ROLES_READ_ALL = "roles:read:all";
    public static final String ROLES_MODIFY_ALL = "roles:modify:all";
    public static final String USER_ROLES_READ_ALL = "user_roles:read:all";
    public static final String USER_ROLES_MODIFY_ALL = "user_roles:modify:all";
}
