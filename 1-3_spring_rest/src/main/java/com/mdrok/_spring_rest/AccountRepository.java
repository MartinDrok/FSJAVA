package com.mdrok._spring_rest;

import com.mdrok._spring_rest.models.Account;
import com.mdrok._spring_rest.models.Holder;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.TreeMap;
import java.util.UUID;

@Repository
public class AccountRepository {

    public static TreeMap<UUID, Account> activeAccounts = new TreeMap<>();
    public static TreeMap<UUID, Account> blockedAccounts = new TreeMap<>();

    public AccountRepository(){ }

    public Account addAccount(Account account){
        try {
            UUID id = UUID.randomUUID();
            account.setId(id);

            for (Holder holder : account.getHolders()){
                if (holder.getId() == null){
                    holder.setId(UUID.randomUUID());
                }
            }

            activeAccounts.put(id, account);

            return account;
        }
        catch (Exception e){
            return null;
        }
    }

    public TreeMap<UUID, Account> getAllActiveAccounts(){

        return activeAccounts;
    }
    public Account getActiveAccount(UUID id){ return activeAccounts.get(id); }

    public Account blockAccount(UUID id){

        try {
            Account account = activeAccounts.remove(id);
            blockedAccounts.put(account.getId(), account);

            return account;
        }
        catch (NullPointerException e){
            return null;
        }
    }

    public Account deleteAccount(UUID id){

        try {
            Account account = activeAccounts.remove(id);

            return account;
        }
        catch (NullPointerException e){
            return null;
        }
    }

    public Holder addHolder(UUID accountId, Holder holder){

        try {
            if (holder.getId() == null){
                holder.setId(UUID.randomUUID());
            }
            Account foundAccount = activeAccounts.get(accountId);
            LinkedList<Holder> holders = foundAccount.getHolders();
            holders.add(holder);
            foundAccount.setHolders(holders);
            activeAccounts.put(accountId, foundAccount);

            return holder;
        }
        catch (Exception e){
            return null;
        }
    }

    public Holder deleteHolder(UUID accountId, UUID holderId){

        try {
            Account foundAccount = activeAccounts.get(accountId);
            LinkedList<Holder> holders = foundAccount.getHolders();

            Holder foundHolder = null;
            for (Holder holder : holders){
                if (holder.getId() != null && holderId.equals(holder.getId())){
                    foundHolder = holder;
                }
            }
            if (foundHolder != null) { holders.remove(foundHolder); }

            foundAccount.setHolders(holders);
            activeAccounts.put(accountId, foundAccount);

            return foundHolder;
        }
        catch (NullPointerException e){
            return null;
        }
    }
}
