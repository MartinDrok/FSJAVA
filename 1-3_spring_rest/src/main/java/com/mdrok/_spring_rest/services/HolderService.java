package com.mdrok._spring_rest.services;

import com.mdrok._spring_rest.entities.Account;
import com.mdrok._spring_rest.entities.Holder;
import com.mdrok._spring_rest.repositories.HolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HolderService {

    private HolderRepository holderRepository;

    @Autowired
    public HolderService(HolderRepository holderRepo){
        holderRepository = holderRepo;
    }

    public Holder addHolder(Holder holder){ return holderRepository.save(holder); }

    public Holder findHolderByAccountIdAndHolderId(UUID accountId, UUID holderId){ return holderRepository.findHolderByAccountIdAndId(accountId, holderId); }

    public void deleteHolderFromAccount(UUID accountId, UUID holderId){ holderRepository.deleteHolderByAccountIdAndId(accountId, holderId); }

    public List<Holder> findAllByAccountId(UUID accountId){ return holderRepository.findAllByAccountId(accountId); }

}
