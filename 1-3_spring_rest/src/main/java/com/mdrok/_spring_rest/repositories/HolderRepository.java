package com.mdrok._spring_rest.repositories;

import com.mdrok._spring_rest.entities.Holder;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface HolderRepository extends CrudRepository<Holder, UUID> {
    @Transactional
    @Modifying(flushAutomatically = true)
    void deleteHolderByAccountIdAndId(UUID accountId, UUID holderId);

    Holder findHolderByAccountIdAndId(UUID accountId, UUID holderId);

    List<Holder> findAllByAccountId(UUID accountId);
}
