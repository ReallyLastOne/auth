package org.reallylastone.auth.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth")
public record ApplicationProperties(Provisioning provisioning) {

    public ApplicationProperties {
        provisioning = provisioning == null ? new Provisioning(List.of()) : provisioning;
    }

    public record Provisioning(List<String> defaultRoles) {

        public Provisioning {
            defaultRoles = defaultRoles == null ? List.of() : List.copyOf(defaultRoles);
        }
    }
}
