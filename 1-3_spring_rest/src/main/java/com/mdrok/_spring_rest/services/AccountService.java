package com.mdrok._spring_rest.services;

import com.mdrok._spring_rest.entities.Account;
import com.mdrok._spring_rest.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private AccountRepository accountRepository;


    @Autowired
    public AccountService(AccountRepository accountRepo){
        accountRepository = accountRepo;
    }

    public Account addAccount(Account account){ return accountRepository.save(account); }

    public Account getActiveAccount(UUID id){ return accountRepository.findById(id); }

    public int blockAccount(UUID id){ return accountRepository.deactivate(id); }

    public void deleteAccount(UUID id){ accountRepository.deleteById(id); }

    public List<Account> getAllAccounts(){ return accountRepository.findAll(); }

    public List<Account> getAllAccountsByHolder(UUID holderId){ return accountRepository.findByHoldersId(holderId);}

}
