package org.reallylastone.auth.keycloak.service;

import java.time.Instant;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.reallylastone.auth.keycloak.domain.dto.KeycloakUser;
import org.reallylastone.auth.user.domain.entity.User;

@Mapper(componentModel = "spring")
public interface KeycloakUserMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "userId", source = "keycloakUser.id")
    @Mapping(target = "username", source = "keycloakUser.username")
    @Mapping(target = "email", source = "keycloakUser.email")
    @Mapping(target = "deleted", constant = "false")
    User toEntity(@MappingTarget User user, KeycloakUser keycloakUser);

}
