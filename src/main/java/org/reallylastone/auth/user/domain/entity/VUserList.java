package org.reallylastone.auth.user.domain.entity;

import static org.reallylastone.common.utils.Constants.AUTH_SCHEMA;

import org.hibernate.annotations.Immutable;
import org.reallylastone.common.model.ViewEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Immutable
@Table(name = "v_user_list", schema = AUTH_SCHEMA)
public class VUserList implements ViewEntity {
    @Id
    private String userId;

    private String username;

    private String email;

    private boolean canEditUserRoles;

    private boolean canEdit;
}
