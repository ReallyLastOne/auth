package org.reallylastone.auth.user.repository;

import java.time.Instant;
import java.util.Collection;

import org.reallylastone.auth.user.domain.entity.User;
import org.reallylastone.common.repository.AuditableRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends AuditableRepository<User, String> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
                update #{#entityName} u
                set u.deleted = true, u.modifiedBy = :modifiedBy, u.modifiedAt = :modifiedAt
                where u.deleted = false and u.userId not in :activeUserIds
            """)
    int markDeletedExcluding(Collection<String> activeUserIds, String modifiedBy,
            Instant modifiedAt);
}
