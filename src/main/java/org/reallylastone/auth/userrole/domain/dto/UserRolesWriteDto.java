package org.reallylastone.auth.userrole.domain.dto;

import java.util.Set;

import jakarta.validation.constraints.NotNull;

public record UserRolesWriteDto(@NotNull Set<@NotNull Long> roleIds) {
}
