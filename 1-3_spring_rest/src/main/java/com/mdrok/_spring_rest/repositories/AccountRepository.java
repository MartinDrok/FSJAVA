package com.mdrok._spring_rest.repositories;

import com.mdrok._spring_rest.entities.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends CrudRepository<Account, UUID> {
    @Transactional
    @Modifying(flushAutomatically = true)
    @Query(nativeQuery = true, value = "update account set account.active = false where account.id = :id")
    Integer deactivate(@Param("id") UUID primaryKey);

    List<Account> findByHoldersId(UUID holderId);
}
