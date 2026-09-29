package org.reallylastone.auth.role.domain.entity;

import static org.reallylastone.common.utils.Constants.AUTH_SCHEMA;

import org.reallylastone.common.model.AuditableEntity;
import org.reallylastone.common.model.HasId;
import org.reallylastone.common.model.HasName;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "roles", schema = AUTH_SCHEMA)
public class Role extends AuditableEntity implements HasId, HasName {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    @SequenceGenerator(name = "role_seq", sequenceName = "role_id_seq", allocationSize = 1)
    private Long roleId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "system")
    private boolean system;

    @Override
    public Long getId() {
        return roleId;
    }
}
