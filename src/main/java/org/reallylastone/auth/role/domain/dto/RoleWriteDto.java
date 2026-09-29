package org.reallylastone.auth.role.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RoleWriteDto(@NotBlank @Size(max = 255) String name, @NotNull Long version) {
    public RoleWriteDto {
        name = name == null ? null : name.strip();
    }
}
