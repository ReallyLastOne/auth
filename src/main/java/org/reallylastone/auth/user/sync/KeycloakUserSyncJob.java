package org.reallylastone.auth.user.sync;


import static org.reallylastone.common.utils.Constants.SYSTEM_USER;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.reallylastone.auth.keycloak.client.KeycloakUserSyncClient;
import org.reallylastone.auth.keycloak.domain.dto.KeycloakUser;
import org.reallylastone.auth.keycloak.service.KeycloakUserMapper;
import org.reallylastone.auth.user.domain.entity.User;
import org.reallylastone.auth.user.repository.UserRepository;
import org.reallylastone.auth.user.service.UserProvisioningService;
import org.reallylastone.common.event.domain.dto.DataModificationEvent;
import org.reallylastone.common.event.service.EventMessageService;
import org.reallylastone.leader_election_lib.LeadershipService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class KeycloakUserSyncJob {

    private final KeycloakUserSyncClient keycloakUserSyncClient;
    private final KeycloakUserMapper keycloakUserMapper;
    private final UserRepository userRepository;
    private final LeadershipService leadershipService;
    private final UserProvisioningService userProvisioningService;
    private final EventMessageService eventMessageService;

    @Scheduled(initialDelayString = "${keycloak.sync.initial-delay-ms:5000}",
            fixedDelayString = "${keycloak.sync.fixed-delay-ms:300000}")
    @Transactional
    public void syncUsers() {
        if (!leadershipService.amILeader())
            return;

        List<KeycloakUser> keycloakUsers = keycloakUserSyncClient.findAllUsers();
        List<String> keycloakUserIds = keycloakUsers.stream().map(KeycloakUser::id).toList();
        Map<String, User> existingUsers = userRepository.findAllById(keycloakUserIds).stream()
                .collect(Collectors.toMap(User::getUserId, Function.identity()));
        int synced = 0;
        Instant now = Instant.now();
        List<User> usersToSave = new ArrayList<>(keycloakUsers.size());
        for (KeycloakUser keycloakUser : keycloakUsers) {
            User user = existingUsers.get(keycloakUser.id());
            if (user == null) {
                if (userProvisioningService.provision(keycloakUser.id(), keycloakUser.username(),
                        keycloakUser.email())) {
                    synced++;
                }
                continue;
            }

            boolean changed = hasUserChanged(keycloakUser, user);
            if (!changed) {
                continue;
            }
            synced++;
            keycloakUserMapper.toEntity(user, keycloakUser);
            usersToSave.add(user);
            if (user.isDeleted()) {
                usersToSave.add(keycloakUserMapper.toEntity(new User(), keycloakUser));
                recordOutbox(keycloakUser, DataModificationEvent.DataModificationAction.DELETED);
            } else {
                recordOutbox(keycloakUser, DataModificationEvent.DataModificationAction.UPDATED);
            }
        }

        userRepository.saveAllAndFlush(usersToSave);

        int removed = keycloakUserIds.isEmpty() ? 0
                : userRepository.markDeletedExcluding(keycloakUserIds, SYSTEM_USER, now);
        if (removed != 0 || synced != 0) {
            log.info("Synced {} users from Keycloak ({} removed)", synced, removed);
        }
    }

    private void recordOutbox(KeycloakUser keycloakUser,
            DataModificationEvent.DataModificationAction action) {
        eventMessageService.recordOutbox("auth", "users", keycloakUser.id(), action, null,
                Instant.now());
    }

    private static boolean hasUserChanged(KeycloakUser keycloakUser, User user) {
        return user.isDeleted() || !Objects.equals(user.getUsername(), keycloakUser.username())
                || !Objects.equals(user.getEmail(), keycloakUser.email());
    }
}
