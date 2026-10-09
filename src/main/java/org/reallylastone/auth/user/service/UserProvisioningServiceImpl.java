package org.reallylastone.auth.user.service;

import java.time.Instant;
import java.util.List;

import org.reallylastone.auth.config.ApplicationProperties;
import org.reallylastone.auth.user.repository.UserRepository;
import org.reallylastone.auth.userrole.repository.UserRoleRepository;
import org.reallylastone.common.event.domain.dto.DataModificationEvent;
import org.reallylastone.common.event.service.EventMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProvisioningServiceImpl implements UserProvisioningService {

    private final UserRepository userRepository;
    private final EventMessageService eventMessageService;
    private final ApplicationProperties applicationProperties;
    private final UserRoleRepository userRoleRepository;

    @Transactional
    public boolean provision(String userId, String username, String email) {
        int inserted = userRepository.insertIfAbsent(userId, username, email);
        if (inserted == 0) {
            return false;
        }
        List<String> defaultRoles = applicationProperties.provisioning().defaultRoles();
        if (!defaultRoles.isEmpty()) {
            userRoleRepository.assignRoles(userId, defaultRoles);
        }

        eventMessageService.recordOutbox("auth", "users", userId,
                DataModificationEvent.DataModificationAction.CREATED, null, Instant.now());
        return true;
    }

}
