package com.mdrok._spring_rest;

import com.mdrok._spring_rest.entities.Account;
import com.mdrok._spring_rest.entities.Holder;
import com.mdrok._spring_rest.services.AccountService;
import com.mdrok._spring_rest.services.HolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("account")
public class AccountRestController {

    private AccountService accountService;
    private HolderService holderService;

    @Autowired
    public AccountRestController(AccountService accService, HolderService holdService){
        accountService = accService;
        holderService = holdService;
    }

    // Account Requests

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable("id") UUID id) {

        Account foundAccount = accountService.getActiveAccount(id);

        if (foundAccount == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(foundAccount, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Account>> getAllAccounts() {

        List<Account> foundAccounts = accountService.getAllAccounts();

        if (foundAccounts == null || foundAccounts.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(foundAccounts, HttpStatus.OK);
    }

    @GetMapping(params = {"holder"})
    public ResponseEntity<List<Account>> getAllAccountsByHolder(@RequestParam("holder") UUID holderId) {

        try{
            List<Account> foundAccounts = accountService.getAllAccountsByHolder(holderId);

            if (foundAccounts == null || foundAccounts.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(foundAccounts, HttpStatus.OK);
        }
        catch (EmptyResultDataAccessException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

    @PostMapping()
    public ResponseEntity<String> createAccount(@RequestBody @Valid Account account) {

        if (account == null){
            return new ResponseEntity<>("The provided account is not in the correct format, please try again.", HttpStatus.BAD_REQUEST);
        }

        Account newAccount = accountService.addAccount(account);

        if (newAccount == null || newAccount.getId() == null) return new ResponseEntity<>("Unable to create a new account, because this service is unavailable at the moment.", HttpStatus.SERVICE_UNAVAILABLE);

        return new ResponseEntity<>("Account " + newAccount.getId() + " successfully created.", HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}", params = {"delete"})
    public ResponseEntity<String> deleteAccount(@PathVariable("id") UUID id, @RequestParam("delete") Boolean delete)  {

        if (delete){
            try{
                accountService.deleteAccount(id);
                return new ResponseEntity<>("Account successfully deleted.", HttpStatus.OK);
            }
            catch(EmptyResultDataAccessException e){
                return new ResponseEntity<>("Unable to delete account with id " + id + ", because an account with this id never existed or was already deleted.", HttpStatus.NO_CONTENT);
            }
            catch(Exception e){
                return new ResponseEntity<>("Unable to delete account, because this service is unavailable at the moment.", HttpStatus.SERVICE_UNAVAILABLE);
            }
        }

        return blockAccount(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> blockAccount(@PathVariable("id") UUID id)  {

        try {
            Integer blockedAccounts = accountService.blockAccount(id);

            if (blockedAccounts != 0) { return new ResponseEntity<>("Account successfully deactivated.", HttpStatus.OK); }

            return new ResponseEntity<>("Unable to deactivate account with id " + id + ", because an account with this id never existed or was already deleted.", HttpStatus.NO_CONTENT);
        }
        catch (Exception e){
            return new ResponseEntity<>("Unable to deactivate account, because this service is unavailable at the moment.", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    // Holder Requests

    @GetMapping("/{id}/holders/{holderId}")
    public ResponseEntity<Holder> getHolder(@PathVariable("id") UUID accountId, @PathVariable("holderId") UUID holderId ) {

        try {
            Holder holder = holderService.findHolderByAccountIdAndHolderId(accountId, holderId);
            return new ResponseEntity<>(holder, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/holders")
    public ResponseEntity<List<Holder>> getAllHolders(@PathVariable("id") UUID accountId) {

        try{
            List<Holder> holders = holderService.findAllByAccountId(accountId);

            if (holders == null || holders.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(holders, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

    @PostMapping("/{id}/holders")
    public ResponseEntity<String> createHolder(@PathVariable("id") UUID accountId, @RequestBody @Valid Holder holder) {

        try{
            if (holder == null){
                return new ResponseEntity<>("The provided holder is not in the correct format, please try again.", HttpStatus.BAD_REQUEST);
            }

            Account foundAccount = accountService.getActiveAccount(accountId);
            if (foundAccount == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            holder.setAccount(foundAccount);
            Holder newHolder = holderService.addHolder(holder);
            if (newHolder == null || newHolder.getId() == null) return new ResponseEntity<>("Unable to add a new holder, because this service is unavailable at the moment.", HttpStatus.SERVICE_UNAVAILABLE);

            return new ResponseEntity<>("Holder " + newHolder.getId() + " successfully added to " + "Account " + accountId + ".", HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>("Unable to add a new holder, because this service is unavailable at the moment.", HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

    @DeleteMapping(value = "/{id}/holders/{holderId}")
    public ResponseEntity<String> deleteHolderFromAccount(@PathVariable("id") UUID accountId, @PathVariable("holderId") UUID holderId)  {
        try{
            holderService.deleteHolderFromAccount(accountId, holderId);
            return new ResponseEntity<>("Holder " + holderId + " successfully deleted from account " + accountId + ".", HttpStatus.OK);
        }
        catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>("Unable to delete holder " + holderId + " from account " + accountId + ", because the account or holder does not exists or has already been deleted.", HttpStatus.NO_CONTENT);
        }
        catch(Exception e){
            return new ResponseEntity<>("Unable to delete holder, because this service is unavailable at the moment.", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    // Request Validation - passes along model validation errors back with the HTTP response
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
