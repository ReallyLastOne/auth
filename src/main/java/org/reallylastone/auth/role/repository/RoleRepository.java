package org.reallylastone.auth.role.repository;

import org.reallylastone.auth.role.domain.entity.Role;
import org.reallylastone.common.repository.AuditableRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends AuditableRepository<Role, Long> {
    @Query("""
            select
                case when count(r) > 0 then true else false end
                from #{#entityName} r
            where lower(r.name) = lower(:name) and r.deleted = false
            and (:excludeId is null or r.roleId <> :excludeId)
            """)
    boolean existsByLowerName(String name, Long excludeId);
}
