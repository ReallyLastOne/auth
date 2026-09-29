package org.reallylastone.auth.userrole.repository;

import java.time.Instant;
import java.util.List;

import org.reallylastone.auth.userrole.domain.dto.UserRoleReadDto;
import org.reallylastone.auth.userrole.domain.entity.UserRole;
import org.reallylastone.common.repository.AuditableRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends AuditableRepository<UserRole, Long> {
    @Query("""
            select new org.reallylastone.auth.userrole.domain.dto.UserRoleReadDto(
                r.roleId,
                r.name,
                case when ur.userRoleId is not null then true else false end
            )
            from Role r
            left join #{#entityName} ur
                on ur.roleId = r.roleId
                and ur.userId = :userId
                and ur.deleted = false
            where r.deleted = false
            """)
    List<UserRoleReadDto> findRolesForUser(String userId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
                update #{#entityName} ur
                set ur.deleted = true, ur.modifiedBy = :modifiedBy, ur.modifiedAt = :modifiedAt
                where ur.userId = :userId and ur.deleted = false
            """)
    void deleteByUserId(String userId, String modifiedBy, Instant modifiedAt);

    boolean existsByRoleIdAndDeletedFalse(Long roleId);
}
