package org.reallylastone.auth.user.filter;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.jspecify.annotations.NonNull;
import org.reallylastone.auth.user.service.UserProvisioningService;
import org.reallylastone.common.permission.utils.JwtAuthoritiesResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.benmanes.caffeine.cache.Caffeine;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserProvisioningFilter extends OncePerRequestFilter {

    public static final int PROVISIONED_USERS_CACHE_SIZE = 50_000;
    public static final Duration PROVISIONED_USERS_CACHE_EXPIRATION = Duration.ofMinutes(30);

    private final UserProvisioningService userProvisioningService;
    private final JwtAuthoritiesResolver jwtAuthoritiesResolver;

    private final Set<String> provisionedUsers = Collections
            .newSetFromMap(Caffeine.newBuilder().maximumSize(PROVISIONED_USERS_CACHE_SIZE)
                    .expireAfterAccess(PROVISIONED_USERS_CACHE_EXPIRATION).<String, Boolean>build()
                    .asMap());

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken token) {
            Jwt jwt = token.getToken();
            String userId = Objects.requireNonNull(jwt.getSubject());
            if (provisionedUsers.add(userId)) {
                try {
                    userProvisioningService.provision(userId,
                            jwt.getClaimAsString("preferred_username"),
                            jwt.getClaimAsString("email"));
                    List<String> authorities =
                            jwtAuthoritiesResolver.getAuthorities(token.getToken());
                    JwtAuthenticationToken updated = new JwtAuthenticationToken(token.getToken(),
                            authorities.stream().map(SimpleGrantedAuthority::new).toList());
                    updated.setDetails(token.getDetails());
                    SecurityContextHolder.getContext().setAuthentication(updated);
                } catch (Exception e) {
                    provisionedUsers.remove(userId);
                    throw e;
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
