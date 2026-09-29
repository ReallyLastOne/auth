package org.reallylastone.auth.role.domain.entity;

import static org.reallylastone.common.utils.Constants.AUTH_SCHEMA;

import org.hibernate.annotations.Immutable;
import org.reallylastone.common.model.ViewEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "v_role_list", schema = AUTH_SCHEMA)
@Getter
@Setter
@Immutable
public class VRoleList implements ViewEntity {
    @Id
    private Long roleId;

    private String name;

    private boolean system;

    private boolean canEdit;

    private boolean canDelete;
}
