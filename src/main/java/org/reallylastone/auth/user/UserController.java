package org.reallylastone.auth.user;

import static org.reallylastone.common.utils.Constants.AUTH_API_PREFIX;

import org.reallylastone.auth.user.domain.entity.VUserList;
import org.reallylastone.auth.user.service.UserService;
import org.reallylastone.auth.utils.Permissions;
import org.reallylastone.common.page.model.DataPage;
import org.reallylastone.common.page.query.QueryPageRequest;
import org.reallylastone.common.page.query.QueryRequest;
import org.reallylastone.common.user.domain.dto.UserInfoDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(AUTH_API_PREFIX + "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/current")
    @Operation(summary = "Gets information about currently authenticated user")
    public UserInfoDto getCurrent() {
        return userService.getCurrent();
    }

    @GetMapping
    @Operation(summary = "Returns a paginated list of users")
    @PreAuthorize("hasAuthority('" + Permissions.USERS_READ_ALL + "')")
    public DataPage<VUserList> getUsers(@QueryRequest @Valid QueryPageRequest request) {
        return userService.getUsers(request);
    }
}
