package org.reallylastone.auth.keycloak.client;

import java.util.ArrayList;
import java.util.List;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.reallylastone.auth.keycloak.domain.dto.KeycloakUser;

public class KeycloakUserSyncClientImpl implements KeycloakUserSyncClient {

    private static final int PAGE_SIZE = 100;

    private final Keycloak keycloak;
    private final String realm;

    public KeycloakUserSyncClientImpl(Keycloak keycloak, String realm) {
        this.keycloak = keycloak;
        this.realm = realm;
    }

    @Override
    public List<KeycloakUser> findAllUsers() {
        UsersResource usersResource = keycloak.realm(realm).users();
        List<KeycloakUser> users = new ArrayList<>();
        List<UserRepresentation> page;
        int first = 0;
        do {
            page = usersResource.list(first, PAGE_SIZE);
            page.forEach(
                    u -> users.add(new KeycloakUser(u.getId(), u.getUsername(), u.getEmail())));
            first += PAGE_SIZE;
        } while (page.size() == PAGE_SIZE);
        return users;
    }
}
