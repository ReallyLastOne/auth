package org.reallylastone.auth.role.repository;

import org.reallylastone.auth.role.domain.entity.VRoleList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VRoleListRepository
        extends JpaRepository<VRoleList, Long>, JpaSpecificationExecutor<VRoleList> {
}
