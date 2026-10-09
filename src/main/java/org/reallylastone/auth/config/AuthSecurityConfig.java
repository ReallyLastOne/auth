package org.reallylastone.auth.config;

import org.reallylastone.auth.user.filter.UserProvisioningFilter;
import org.reallylastone.auth.user.service.UserProvisioningService;
import org.reallylastone.common.config.HttpSecurityCustomizer;
import org.reallylastone.common.permission.utils.JwtAuthoritiesResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;

@Configuration
public class AuthSecurityConfig {
    @Bean
    public HttpSecurityCustomizer userProvisioningCustomizer(
            UserProvisioningService userProvisioningService,
            JwtAuthoritiesResolver jwtAuthoritiesResolver) {
        return http -> http.addFilterAfter(
                new UserProvisioningFilter(userProvisioningService, jwtAuthoritiesResolver),
                BearerTokenAuthenticationFilter.class);
    }
}
