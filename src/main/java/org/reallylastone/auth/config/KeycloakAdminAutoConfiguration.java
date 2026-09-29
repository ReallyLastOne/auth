package org.reallylastone.auth.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.reallylastone.auth.keycloak.client.KeycloakUserSyncClient;
import org.reallylastone.auth.keycloak.client.KeycloakUserSyncClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakAdminAutoConfiguration {

    @Bean
    public Keycloak keycloakAdminClient(@Value("${keycloak.server-url}") String serverUrl,
            @Value("${keycloak.admin-realm:master}") String adminRealm,
            @Value("${keycloak.admin-client-id:admin-cli}") String clientId,
            @Value("${keycloak.admin-username}") String username,
            @Value("${keycloak.admin-password}") String password) {
        return KeycloakBuilder.builder().serverUrl(serverUrl).realm(adminRealm).clientId(clientId)
                .username(username).password(password).grantType(OAuth2Constants.PASSWORD).build();
    }

    @Bean
    public KeycloakUserSyncClient keycloakUserSyncClient(Keycloak keycloakAdminClient,
            @Value("${keycloak.realm}") String realm) {
        return new KeycloakUserSyncClientImpl(keycloakAdminClient, realm);
    }
}
