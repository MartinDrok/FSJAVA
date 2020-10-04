package com.mdrok._spring_rest;

import com.mdrok._spring_rest.models.Account;
import com.mdrok._spring_rest.models.Holder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

@Service
public class AccountService {

    private AccountRepository _accountRepo;

    public AccountService(AccountRepository accountRepo){
        _accountRepo = accountRepo;
    }

    public Account addAccount(Account account){ return _accountRepo.addAccount(account); }

    public Account getActiveAccount(UUID id){ return _accountRepo.getActiveAccount(id); }

    public Account blockAccount(UUID id){ return _accountRepo.blockAccount(id); }

    public Account deleteAccount(UUID id){ return _accountRepo.deleteAccount(id); }

    public List<Account> getAllAccounts(){

        TreeMap<UUID, Account> activeAccounts = _accountRepo.getAllActiveAccounts();

        LinkedList<Account> accounts = new LinkedList<>();

        for (UUID id : activeAccounts.keySet()){
            accounts.add(activeAccounts.get(id));
        }

        return accounts;
    }

    public List<Account> getAllAccountsByHolder(UUID holderId){
        TreeMap<UUID, Account> activeAccounts = _accountRepo.getAllActiveAccounts();

        LinkedList<Account> accounts = new LinkedList<>();

        for (Account account : activeAccounts.values()){
            for (Holder holder : account.getHolders()){
                if (holder.getId() != null && holderId.equals(holder.getId())){
                    accounts.add(account);
                }
            }
        }

        return accounts;
    }

    public Holder addHolder(UUID accountId, Holder holder){ return _accountRepo.addHolder(accountId, holder); }

    public Holder deleteHolder(UUID accountId, UUID holderId){ return _accountRepo.deleteHolder(accountId, holderId); }
}
