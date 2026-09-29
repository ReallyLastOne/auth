package org.reallylastone.auth.user.domain.entity;

import static org.reallylastone.common.utils.Constants.AUTH_SCHEMA;

import java.util.HashSet;
import java.util.Set;

import org.reallylastone.auth.userrole.domain.entity.UserRole;
import org.reallylastone.common.model.AuditableEntity;
import org.reallylastone.common.model.HasUserId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users", schema = AUTH_SCHEMA)
@Getter
@Setter
public class User extends AuditableEntity implements HasUserId {
    @Id
    @Column(name = "user_id", columnDefinition = "uuid")
    private String userId;

    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @OneToMany
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Set<UserRole> userRoles = new HashSet<>();
}
