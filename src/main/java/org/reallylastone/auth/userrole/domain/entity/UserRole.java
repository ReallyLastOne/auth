package org.reallylastone.auth.userrole.domain.entity;

import org.reallylastone.auth.role.domain.entity.Role;
import org.reallylastone.auth.user.domain.entity.User;
import org.reallylastone.common.model.AuditableEntity;
import org.reallylastone.common.model.HasId;
import org.reallylastone.common.model.HasUserId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import static org.reallylastone.common.utils.Constants.AUTH_SCHEMA;

@Entity
@Table(name = "user_roles", schema = AUTH_SCHEMA)
@Getter
@Setter
public class UserRole extends AuditableEntity implements HasUserId, HasId {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_seq")
    @SequenceGenerator(name = "user_role_seq", sequenceName = "user_role_id_seq",
            allocationSize = 1)
    @Column(name = "user_role_id")
    private Long userRoleId;

    @Column(name = "user_id", nullable = false, columnDefinition = "uuid")
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private Role role;

    @Override
    public Long getId() {
        return userRoleId;
    }
}
