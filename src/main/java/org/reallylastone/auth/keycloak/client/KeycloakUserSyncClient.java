package org.reallylastone.auth.keycloak.client;

import java.util.List;

import org.reallylastone.auth.keycloak.domain.dto.KeycloakUser;

public interface KeycloakUserSyncClient {

    List<KeycloakUser> findAllUsers();
}
