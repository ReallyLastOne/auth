package org.reallylastone.auth.user.repository;

import org.reallylastone.auth.user.domain.entity.VUserList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VUserListRepository
        extends JpaRepository<VUserList, String>, JpaSpecificationExecutor<VUserList> {
}
