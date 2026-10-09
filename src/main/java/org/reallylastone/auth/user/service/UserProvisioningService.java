package org.reallylastone.auth.user.service;

public interface UserProvisioningService {
    boolean provision(String userId, String username, String email);
}
